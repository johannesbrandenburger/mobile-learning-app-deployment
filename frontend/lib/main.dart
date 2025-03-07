import 'package:flutter/material.dart';
import 'package:frontend/global.dart';
import 'package:frontend/pages/feedback/attend_feedback_page.dart';
import 'package:frontend/pages/feedback/feedback_preview_page.dart';
import 'package:frontend/pages/feedback/feedback_result_page.dart';
import 'package:frontend/pages/login_page.dart';
import 'package:frontend/pages/main_page.dart';
import 'package:frontend/pages/quiz/attend_quiz_page.dart';
import 'package:frontend/pages/quiz/quiz_control_page.dart';
import 'package:frontend/pages/quiz/quiz_preview_page.dart';
import 'package:frontend/theme/themes.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    initPreferences();

    return MaterialApp(
      title: 'Flutter Demo',
      theme: getTheme(lightColorScheme),
      initialRoute: '/login',
      routes: <String, WidgetBuilder>{
        '/login': (_) => const LoginPage(),
        '/main': (_) => const MainPage(),
        '/feedback-info': (context) {
          var arguments = ModalRoute.of(context)!.settings.arguments
              as Map<String, dynamic>?;
          if (arguments == null) {
            return const MainPage();
          }
          return FeedbackPreviewPage(
            courseId: arguments["courseId"],
            formId: arguments["formId"],
          );
        },
        '/attend-feedback': (context) {
          var code = ModalRoute.of(context)!.settings.arguments as String?;
          if (code == null) {
            return const MainPage();
          }
          return AttendFeedbackPage(code: code);
        },
        '/feedback-result': (context) {
          var arguments = ModalRoute.of(context)!.settings.arguments
              as Map<String, dynamic>?;
          if (arguments == null) {
            return const MainPage();
          }
          return FeedbackResultPage(
            courseId: arguments["courseId"],
            formId: arguments["formId"],
          );
        },
        // '/history-feedback': (_) => const HistoryFeedbackPage(),
        '/quiz-info': (context) {
          var arguments = ModalRoute.of(context)!.settings.arguments
              as Map<String, dynamic>?;
          if (arguments == null) {
            return const MainPage();
          }
          return QuizPreviewPage(
            courseId: arguments["courseId"],
            formId: arguments["formId"],
          );
        },
        '/attend-quiz': (context) {
          var code = ModalRoute.of(context)!.settings.arguments as String?;
          if (code == null) {
            return const MainPage();
          }
          return AttendQuizPage(code: code);
        },
        '/quiz-control': (context) {
          var arguments = ModalRoute.of(context)!.settings.arguments
              as Map<String, dynamic>?;
          if (arguments == null) {
            return const MainPage();
          }
          return QuizControlPage(
            courseId: arguments["courseId"],
            formId: arguments["formId"],
          );
        },
      },
    );
  }
}
