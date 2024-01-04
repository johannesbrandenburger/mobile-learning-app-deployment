import 'package:frontend/models/feedback/feedback_question.dart';
import 'package:frontend/models/form.dart';

class FeedbackForm extends Form {

  FeedbackForm({
    required super.id,
    required super.courseId,
    required super.name,
    required super.description,
    required super.connectCode,
    required super.status,
    required super.questions,
  });

  factory FeedbackForm.fromJson(Map<String, dynamic> json) {
    return FeedbackForm(
      id: json['id'],
      courseId: json['courseId'],
      name: json['name'],
      description: json['description'],
      connectCode: (json['connectCode'] as int).toString(),
      status: json['status'],
      questions: json['questions'] == null
          ? []
          : (json['questions'] as List<dynamic>)
              .map((e) => FeedbackQuestion.fromJson(e['questionContent']))
              .toList(),
    );
  }
}
