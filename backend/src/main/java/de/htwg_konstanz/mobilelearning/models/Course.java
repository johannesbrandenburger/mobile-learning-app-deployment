package de.htwg_konstanz.mobilelearning.models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import de.htwg_konstanz.mobilelearning.models.auth.User;
import de.htwg_konstanz.mobilelearning.models.feedback.FeedbackForm;
import de.htwg_konstanz.mobilelearning.models.feedback.FeedbackQuestion;
import de.htwg_konstanz.mobilelearning.models.quiz.QuizForm;
import de.htwg_konstanz.mobilelearning.models.quiz.QuizQuestion;
import de.htwg_konstanz.mobilelearning.services.api.models.ApiCourse;
import de.htwg_konstanz.mobilelearning.services.api.models.ApiFeedbackForm;
import de.htwg_konstanz.mobilelearning.services.api.models.ApiQuizForm;


public class Course implements Serializable {
    public ObjectId id;
    public String name;
    public String description;
    public List<ObjectId> owners;
    public String key;

    // feedback
    public List<FeedbackForm> feedbackForms;
    public List<FeedbackQuestion> feedbackQuestions;

    // quiz
    public List<QuizForm> quizForms;
    public List<QuizQuestion> quizQuestions;

    public Course() {
    }

    // TODO: create different constructors
    public Course(String name, String description) {
        this.id = new ObjectId();
        this.name = name;
        this.description = description;
        this.owners = new ArrayList<ObjectId>();
        this.feedbackForms = new ArrayList<FeedbackForm>();
        this.feedbackQuestions = new ArrayList<FeedbackQuestion>();
        this.quizForms = new ArrayList<QuizForm>();
        this.quizQuestions = new ArrayList<QuizQuestion>();
        this.key = "";
    }

    // id
    public ObjectId getId() {
        return this.id;
    }

    // name
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // owners
    public List<ObjectId> getOwners() {
        return this.owners;
    }

    public void addOwner(ObjectId owner) {
        this.owners.add(owner);
    }

    public void removeOwner(ObjectId owner) {
        this.owners.remove(owner);
    }

    public void setOwners(List<ObjectId> owners) {
        this.owners = owners;
    }

    public boolean isOwner(String userId) {
        return this.owners.contains(new ObjectId(userId));
    }

    public boolean isOwner(ObjectId userId) {
        return this.owners.contains(userId);
    }

    public boolean isOwner(User user) {
        return this.owners.contains(user.getId());
    }

    // description
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // feedback
    // feedbackForms
    public List<FeedbackForm> getFeedbackForms() {
        return this.feedbackForms;
    }

    public FeedbackForm getFeedbackFormById(ObjectId feedbackFormId) {
        for (FeedbackForm feedbackForm : this.feedbackForms) {
            if (feedbackForm.getId().equals(feedbackFormId)) {
                return feedbackForm;
            }
        }
        return null;
    }

    public FeedbackForm getFeedbackFormByConnectCode(Integer connectCode) {
        for (FeedbackForm feedbackForm : this.feedbackForms) {
            if (feedbackForm.getConnectCode().equals(connectCode)) {
                return feedbackForm;
            }
        }
        return null;
    }

    public FeedbackForm getFeedbackFormByKey(String key) {
        for (FeedbackForm feedbackForm : this.feedbackForms) {
            if (feedbackForm.getKey().equals(key)) {
                return feedbackForm;
            }
        }
        return null;
    }

    public void addFeedbackForm(FeedbackForm feedbackForm) {
        this.feedbackForms.add(feedbackForm);
    }    

    public void removeFeedbackForm(FeedbackForm feedbackForm) {
        this.feedbackForms.remove(feedbackForm);
    }    

    public void setFeedbackForms(List<FeedbackForm> feedbackForms) {
        this.feedbackForms = feedbackForms;
    }    

    // feedbackQuestions
    public List<FeedbackQuestion> getFeedbackQuestions() {
        return this.feedbackQuestions;
    }

    public FeedbackQuestion getFeedbackQuestionById(ObjectId feedbackQuestionId) {
        for (FeedbackQuestion feedbackQuestion : this.feedbackQuestions) {
            if (feedbackQuestion.getId().equals(feedbackQuestionId)) {
                return feedbackQuestion;
            }
        }
        return null;
    }

    public FeedbackQuestion getFeedbackQuestionByKey(String key) {
        for (FeedbackQuestion feedbackQuestion : this.feedbackQuestions) {
            if (feedbackQuestion.getKey().equals(key)) {
                return feedbackQuestion;
            }
        }
        return null;
    }

    public void setFeedbackQuestions(List<FeedbackQuestion> feedbackQuestions) {
        this.feedbackQuestions = feedbackQuestions;
    }

    public void addFeedbackQuestion(FeedbackQuestion feedbackQuestion) {
        this.feedbackQuestions.add(feedbackQuestion);
    }

    public void removeFeedbackQuestion(FeedbackQuestion feedbackQuestion) {
        this.feedbackQuestions.remove(feedbackQuestion);
    }

    // quiz
    // quizForms
    public List<QuizForm> getQuizForms() {
        return this.quizForms;
    }

    public QuizForm getQuizFormById(ObjectId quizFormId) {
        for (QuizForm quizForm : this.quizForms) {
            if (quizForm.getId().equals(quizFormId)) {
                return quizForm;
            }
        }
        return null;
    }

    public QuizForm getQuizFormByConnectCode(Integer connectCode) {
        for (QuizForm quizForm : this.quizForms) {
            if (quizForm.getConnectCode().equals(connectCode)) {
                return quizForm;
            }
        }
        return null;
    }

    public QuizForm getQuizFormByKey(String key) {
        for (QuizForm quizForm : this.quizForms) {
            if (quizForm.getKey().equals(key)) {
                return quizForm;
            }
        }
        return null;
    }

    public void addQuizForm(QuizForm quizForm) {
        this.quizForms.add(quizForm);
    }

    public void removeQuizForm(QuizForm quizForm) {
        this.quizForms.remove(quizForm);
    }

    public void setQuizForms(List<QuizForm> quizForms) {
        this.quizForms = quizForms;
    }

    // quizQuestions
    public List<QuizQuestion> getQuizQuestions() {
        return this.quizQuestions;
    }

    public QuizQuestion getQuizQuestionById(ObjectId quizQuestionId) {
        for (QuizQuestion quizQuestion : this.quizQuestions) {
            if (quizQuestion.getId().equals(quizQuestionId)) {
                return quizQuestion;
            }
        }
        return null;
    }

    public QuizQuestion getQuizQuestionByKey(String key) {
        for (QuizQuestion quizQuestion : this.quizQuestions) {
            if (quizQuestion.getKey().equals(key)) {
                return quizQuestion;
            }
        }
        return null;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public void addQuizQuestion(QuizQuestion quizQuestion) {
        this.quizQuestions.add(quizQuestion);
    }

    public void removeQuizQuestion(QuizQuestion quizQuestion) {
        this.quizQuestions.remove(quizQuestion);
    }

    // general form
    public Form getFormById(ObjectId formId) {
        List<Form> forms = new ArrayList<Form>();
        forms.addAll(this.feedbackForms);
        forms.addAll(this.quizForms);
        for (Form form : forms) {
            if (form.getId().equals(formId)) {
                return form;
            }
        }
        return null;
    }

    public Form getFormByConnectCode(Integer connectCode) {
        List<Form> forms = new ArrayList<Form>();
        forms.addAll(this.feedbackForms);
        forms.addAll(this.quizForms);
        for (Form form : forms) {
            if (form.getConnectCode().equals(connectCode)) {
                return form;
            }
        }
        return null;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public static Course fromApiCourse(ApiCourse apiCourse) throws IllegalArgumentException {
        
        // validate input
        if (apiCourse == null) {
            throw new IllegalArgumentException("Course must not be null");
        }
        if (apiCourse.getName() == null || apiCourse.getName().isEmpty()) {
            throw new IllegalArgumentException("Course name must not be null");
        }
        if (apiCourse.getDescription() == null || apiCourse.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Course description must not be null");
        }
        if (apiCourse.getKey() == null || apiCourse.getKey().isEmpty()) {
            throw new IllegalArgumentException("Course key must not be null");
        }

        // create course
        Course course = new Course(apiCourse.getName(), apiCourse.getDescription());
        course.setKey(apiCourse.getKey());

        // create the feedback forms
        for (ApiFeedbackForm apiFeedbackForm : apiCourse.getFeedbackForms()) {
            FeedbackForm feedbackForm = FeedbackForm.fromApiFeedbackForm(apiFeedbackForm, course);
            feedbackForm.setKey(apiFeedbackForm.getKey());
            course.addFeedbackForm(feedbackForm);
        }

        // create the quiz forms
        for (ApiQuizForm apiQuizForm : apiCourse.getQuizForms()) {
            QuizForm quizForm = QuizForm.fromApiQuizForm(apiQuizForm, course);
            quizForm.setKey(apiQuizForm.getKey());
            course.addQuizForm(quizForm);
        }

        return course;
    }

    public void updateFromApiCourse(ApiCourse apiCourse) {

        // validate input
        if (apiCourse == null) {
            throw new IllegalArgumentException("Course must not be null");
        }
        if (apiCourse.getName() == null || apiCourse.getName().isEmpty()) {
            throw new IllegalArgumentException("Course name must not be null");
        }
        if (apiCourse.getDescription() == null || apiCourse.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Course description must not be null");
        }

        // update course
        this.setName(apiCourse.getName());
        this.setDescription(apiCourse.getDescription());

        // update feedback forms
        for (ApiFeedbackForm apiFeedbackForm : apiCourse.getFeedbackForms()) {
            FeedbackForm feedbackForm = this.getFeedbackFormByKey(apiFeedbackForm.getKey());
            if (feedbackForm == null) {
                feedbackForm = FeedbackForm.fromApiFeedbackForm(apiFeedbackForm, this);
                this.addFeedbackForm(feedbackForm);
            } else {
                feedbackForm.updateFromApiFeedbackForm(apiFeedbackForm, this);
            }
        }

        // update quiz forms
        for (ApiQuizForm apiQuizForm : apiCourse.getQuizForms()) {
            QuizForm quizForm = this.getQuizFormByKey(apiQuizForm.getKey());
            if (quizForm == null) {
                quizForm = QuizForm.fromApiQuizForm(apiQuizForm, this);
                this.addQuizForm(quizForm);
            } else {
                quizForm.updateFromApiQuizForm(apiQuizForm, this);
            }
        }
    }

}