import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:frontend/global.dart';
import 'package:frontend/models/quiz/quiz_form.dart';
import 'package:frontend/utils.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:http/http.dart' as http;

class QuizControlPage extends StatefulWidget {
  final String courseId;
  final String formId;

  const QuizControlPage(
      {super.key, required this.courseId, required this.formId});

  @override
  State<QuizControlPage> createState() => _QuizControlPageState();
}

class _QuizControlPageState extends State<QuizControlPage> {
  bool _loading = true;

  late String _courseId;
  late String _formId;
  late String _userId;
  late List<String> _roles;

  late QuizForm _form;
  late String _status;
  WebSocketChannel? _socketChannel;

  late List<Map<String, dynamic>> _results;

  @override
  void initState() {
    super.initState();

    _userId = getSession()!.userId;
    _roles = getSession()!.roles;
    init();
  }

  Future init() async {
    _courseId = widget.courseId;
    _formId = widget.formId;
    fetchForm();
  }

  Future fetchForm() async {
    try {
      final response = await http.get(
        Uri.parse(
            "${getBackendUrl()}/course/$_courseId/quiz/form/$_formId?results=true"),
        headers: {
          "Content-Type": "application/json",
          "AUTHORIZATION": "Bearer ${getSession()!.jwt}",
        },
      );
      if (response.statusCode == 200) {
        var data = jsonDecode(response.body);
        startWebsocket();
        setState(() {
          _form = QuizForm.fromJson(data);
          _results = getResults(data);
          _status = data["status"];
          _loading = false;
        });
      }
    } on http.ClientException catch (_) {
      // TODO: handle error
    }
  }

  void startWebsocket() {
    _socketChannel = WebSocketChannel.connect(
      Uri.parse(
          "${getBackendUrl(protocol: "ws")}/course/$_courseId/quiz/form/$_formId/subscribe/$_userId/${getSession()!.jwt}"),
    );

    _socketChannel!.stream.listen((event) {
      var data = jsonDecode(event);
      if (data["action"] == "FORM_STATUS_CHANGED") {
        setState(() {
          _status = data["formStatus"];
        });
      }
      if (data["action"] == "RESULT_ADDED") {
        setState(() {
          _results = getResults(data["form"]);
        });
      }
    }, onError: (error) {
      setState(() {
        _status = "ERROR";
      });
    });
  }

  void startForm() {
    if (_socketChannel != null) {
      _socketChannel!.sink.add(jsonEncode({
        "action": "CHANGE_FORM_STATUS",
        "formStatus": "STARTED",
        "roles": _roles,
        "userId": _userId,
      }));
    }
  }

  void stopForm() {
    if (_socketChannel != null) {
      _socketChannel!.sink.add(jsonEncode({
        "action": "CHANGE_FORM_STATUS",
        "formStatus": "FINISHED",
        "roles": _roles,
        "userId": _userId,
      }));
    }
  }

  void resetForm() {
    if (_socketChannel != null) {
      _socketChannel!.sink.add(jsonEncode({
        "action": "CHANGE_FORM_STATUS",
        "formStatus": "NOT_STARTED",
        "roles": _roles,
        "userId": _userId,
      }));
    }
  }

  List<Map<String, dynamic>> getResults(Map<String, dynamic> json) {
    List<dynamic> elements = json["questions"];
    return elements.map((element) {
      List<dynamic> results = element["results"];
      List<int> resultValues =
          results.map((result) => int.parse(result["value"])).toList();
      double average = 0;
      if (resultValues.isNotEmpty) {
        average = resultValues.reduce((curr, next) => curr + next) /
            resultValues.length;
      }
      return {"values": resultValues, "average": average};
    }).toList();
  }

  @override
  void dispose() {
    _socketChannel?.sink.close();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    if (_loading) {
      return const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }

    final colors = Theme.of(context).colorScheme;

    if (_status == "NOT_STARTED") {
      var code = _form.connectCode;
      code = "${code.substring(0, 3)} ${code.substring(3, 6)}";

      return Scaffold(
        appBar: AppBar(
          title: Text(_form.name,
              style: const TextStyle(
                  color: Colors.white, fontWeight: FontWeight.bold)),
          backgroundColor: colors.primary,
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(
                code,
                style: Theme.of(context).textTheme.headlineMedium,
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: startForm,
                child: const Text('Quiz starten'),
              ),
            ],
          ),
        ),
      );
    }

    return Scaffold(
        appBar: AppBar(
          title: Text(_form.name,
              style: const TextStyle(
                  color: Colors.white, fontWeight: FontWeight.bold)),
          backgroundColor: colors.primary,
        ),
        body: Stack(
          children: [
            SingleChildScrollView(
              child: Column(
                children: <Widget>[
                  const SizedBox(height: 16),
                  Column(
                    children: [
                      ListView.builder(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        itemCount: _form.questions.length,
                        itemBuilder: (context, index) {
                          final element = _form.questions[index];
                          final double average = _results[index]["average"];
                          final roundAverage = (average * 100).round() / 100;
                          final values = _results[index]["values"];
                          return Padding(
                            padding: const EdgeInsets.all(32.0),
                            child: Column(
                              children: <Widget>[
                                Text(element.name,
                                    style: const TextStyle(
                                        fontSize: 24,
                                        fontWeight: FontWeight.bold)),
                                Text(element.description,
                                    style: const TextStyle(fontSize: 15),
                                    textAlign: TextAlign.center),
                                  Text("$roundAverage",
                                      style: const TextStyle(fontSize: 20),
                                      textAlign: TextAlign.center),
                              ],
                            ),
                          );
                        },
                      ),
                    ],
                  ),
                  if (_status == "STARTED")
                    ElevatedButton(
                      onPressed: stopForm,
                      child: const Text('Quiz beenden'),
                    ),
                  if (_status == "FINISHED")
                    Column(
                      children: [
                        ElevatedButton(
                          onPressed: startForm,
                          child: const Text('Quiz fortsetzen'),
                        ),
                        const SizedBox(height: 8),
                        ElevatedButton(
                          onPressed: resetForm,
                          child: Text('Quiz zurücksetzen',
                              style: TextStyle(color: colors.error)),
                        ),
                      ],
                    ),
                  const SizedBox(height: 32),
                ],
              ),
            ),
            Positioned(
              top: 0,
              left: 0,
              right: 0,
              child: Container(
                color: colors.surfaceVariant,
                child: Text(
                  "${_form.connectCode.substring(0, 3)} ${_form.connectCode.substring(3, 6)}",
                  style: Theme.of(context).textTheme.headlineSmall,
                  textAlign: TextAlign.center,
                ),
              ),
            )
          ],
        ));
  }
}
// 