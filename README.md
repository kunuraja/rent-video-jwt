Rent-video is a Spring Boot app that manages video rental. This application uses JWT security for authentication of users. There are 2 authorization roles,
Customer and Admin. Users can register in the application. Admins can create, update, or delete videos. Customers can view the list of available or unavailable videos.
Customer can rent a video if the video is available and number of rentals is less than two i.e one user can't have more than 2 rentals of videos. When teh customer returns
teh videos, the number of rentals under his name is updated.

Requirements:

For building and running the application you need:

JDK 21

Gradle

MySql

Running the application locally:

You can run the spring-boot application locally by using the gradle command:

./gradlew bootrun

Endpoints for accessing the application:

POST /videos - For adding videos

PUT /videos/{videoId} - For updating a video

DELETE /videos/{videoId} - For deleting a video

GET /videos - For getting the list of videos (Both available and unavailable)

PUT /users/{userId}/videos/{videoId}/rent - Applying for rent (Customer)

PUT /users/{userId}/videos/{videoId}/return - Returning a video (Customer)

The postman collection is added to this project repository.

PUT /students/{studentId}/subjects/{subjectId}/exams/{examId} - For registerin a registered student into the exam for the corresponding subject
