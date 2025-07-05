# Student Study Analyzer

**Student Study Analyzer** is an Android application designed to streamline and digitize the management of student assignments, notes, videos, fees, and assessments for educational institutions. The app provides dedicated interfaces for students, teachers, and administrators, enabling efficient communication, resource sharing, and academic tracking.

## Features

### 1. Multi-Role Support
- **Admin Panel**: Manage faculty, students, and database records. Access detailed student and faculty information.
- **Teacher Panel**: Upload assignments, notes, and videos. Track student submissions and generate reports.
- **Student Panel**: Access and submit assignments, view notes and videos, check updates, and pay fees online.

### 2. Assignment Management
- Teachers can upload assignments and track which students have viewed or submitted them.
- Students can view and submit assignments directly through the app.
- Excel reports for assignment submissions and pending students can be generated and downloaded.

### 3. Notes & Video Sharing
- Teachers can upload notes and educational videos.
- Students can access notes and videos categorized by subject and year.

### 4. Fee Management
- Students can pay fees online via Razorpay integration.
- Admins can view and export fee payment details as Excel files.

### 5. Assessment & Analytics
- Teachers can assess student submissions and generate analytics.
- Admins and teachers can download reports in Excel format for further analysis.

### 6. Notifications & Updates
- Students receive updates and notifications about new assignments, notes, and videos.

### 7. Secure Login & Role-Based Access
- Separate login flows for admin, teacher, and student.
- Firebase Authentication and Realtime Database integration.

## Project Structure

```
app/
  └── src/
      └── main/
          ├── java/com/page_1/app/
          │   ├── admin/         # Admin activities and database management
          │   ├── DisplayFiles/  # Assignment, notes, and video display logic
          │   ├── recycleFaculty/
          │   ├── recycleStudent/
          │   ├── student/       # Student activities and UI
          │   ├── teacher/       # Teacher activities and UI
          │   └── utils/         # Utility classes
          └── res/
              ├── layout/        # XML layout files for activities and fragments
              ├── drawable/      # Icons and images
              └── values/        # Strings, colors, styles
```

## Key Technologies

- **Java** (Android)
- **Firebase** (Authentication, Realtime Database)
- **Razorpay** (Payment Integration)
- **Apache POI** (Excel file generation)
- **RecyclerView, ListView, CardView** (UI Components)

## App Workflow Screenshots

## App Workflow Screenshots

<table>
  <tr>
    <td align="center"><img src="page-screenshots/accdetails.png" width="200"/><br/>accdetails.png</td>
    <td align="center"><img src="page-screenshots/home.png" width="200"/><br/>home.png</td>
    <td align="center"><img src="page-screenshots/acc.png" width="200"/><br/>acc.png</td>
  </tr>
  <tr>
    <td align="center"><img src="page-screenshots/razorpay.png" width="200"/><br/>razorpay.png</td>
    <td align="center"><img src="page-screenshots/addteather.png" width="200"/><br/>addteather.png</td>
    <td align="center"><img src="page-screenshots/studenthome.png" width="200"/><br/>studenthome.png</td>
  </tr>
  <tr>
    <td align="center"><img src="page-screenshots/adminhome.png" width="200"/><br/>adminhome.png</td>
    <td align="center"><img src="page-screenshots/studentlogin.png" width="200"/><br/>studentlogin.png</td>
    <td align="center"><img src="page-screenshots/adminlogin.png" width="200"/><br/>adminlogin.png</td>
  </tr>
  <tr>
    <td align="center"><img src="page-screenshots/studentresponse.png" width="200"/><br/>studentresponse.png</td>
    <td align="center"><img src="page-screenshots/adstudent.png" width="200"/><br/>adstudent.png</td>
    <td align="center"><img src="page-screenshots/teacheranalysis.png" width="200"/><br/>teacheranalysis.png</td>
  </tr>
  <tr>
    <td align="center"><img src="page-screenshots/teacherhome.png" width="200"/><br/>teacherhome.png</td>
    <td align="center"><img src="page-screenshots/teacherlogin.png" width="200"/><br/>teacherlogin.png</td>
    <td align="center"><img src="page-screenshots/teacherupload.png" width="200"/><br/>teacherupload.png</td>
  </tr>
  <tr>
    <td align="center"><img src="page-screenshots/dialog.png" width="200"/><br/>dialog.png</td>
    <td align="center"><img src="page-screenshots/feesadmin.png" width="200"/><br/>feesadmin.png</td>
    <td align="center"><img src="page-screenshots/feeshome.png" width="200"/><br/>feeshome.png</td>
  </tr>
  <tr>
    <td align="center"><img src="page-screenshots/forgotdetails.png" width="200"/><br/>forgotdetails.png</td>
    <td align="center"><img src="page-screenshots/viewdocs.png" width="200"/><br/>viewdocs.png</td>
    <td align="center"></td>
  </tr>
</table>

**[Download App](https://github.com/Sameer377/Student-Study-Analyzer/blob/master/app/debug/app-debug.apk)**


**Developed by [Sameer Shaikh](https://github.com/Sameer377/)**
