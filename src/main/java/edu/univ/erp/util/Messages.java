package edu.univ.erp.util;

public class Messages {

    public static final String INCORRECT_CREDENTIALS = " You've either entered incorrect credentials or your account does not exist.";
    public static final String EMPTY_FIELD = "Text Fields must not be empty.";
    public static final String DB_CONNECTION_ERROR = "Unable to connect to the Database. Please try again later.";
    public static final String TAB_OPEN_ERROR = "Unable to open tab! Please try again.";
    public static final String NO_GRADES_ERROR = "You don't have any grades to be displayed.";
    public static final String DOWNLOAD_FAILED_ERROR = "Download failed. Please try again.";
    public static final String MESSAGE_LOGGING_ERROR = "Couldn't log the message. Please try again";
    public static final String NO_ADDABLE_COURSES = "Currently no courses available for adding";
    public static final String TOO_MANY_COURSES = "You are only allowed to select 5 courses!";
    public static final String NO_SELECTED_COURSE = "You haven't selected any courses";
    public static final String NOT_GRADED_ERROR = "Class must be graded in order to view statistics";
    public static final String NO_SECTION_ERROR = "No sections have been allotted to you so far!";
    public static final String FAILED_SAVE_ERROR = "Unable to save changes";
    public static final String BOOTUP_FAIL_ERROR = "Failed to start. please try again.";
    public static final String UNIQUE_VALUE_ERR = "Value supplied at id must be unique";
    public static final String NON_SENSICAL_VALUE = "Non numeric value entered";
    public static final String DUPLICATE_VALUE = "You are trying to register for the same course again which isn't allowed";
    public static final String NON_EXISTENT_PREREQ = "Pre-requisite doesn't exist in the table";
    public static final String WRONG_TIME_FORMAT = "Time must be stored in HH:MM:SS format";
    public static final String NO_SELECTED_COURSES = "You didn't select any courses.";
    public static final String DEADLINE_EXPIRED = "Deadline for altering courses expired";
    public static final String NO_GRADES_MAINT = "You cannot look at your grades while under maintainence mode";
    public static final String NOT_ALLOWED = "You cannot alter courses during maintainence mode";
    public static final String NO_GRIEVANCE = "You cannot log grievances during maintainence mode";
    public static final String NO_GRADES_UPLOAD = "You cannot upload grades under maintainence mode";
    public static final String GENERIC_ERROR = "Oops an unexpected error occurred! Please try again!";
    public static final String ACC_NO_EXIST = "It looks like you don't have an account. Contact the admin for more info.";

    public static final String CSV_LOADING_COMPLETE="Csv loaded successfully";
    public static final String DOWNLOAD_STATUS = "Download in progress...";
    public static final String DOWNLOAD_COMPLETE = "Download Complete!";
    public static final String MESSAGE_LOGGED = "Your grievance has been lodged successfully!";
    public static final String NO_COURSES_AVAILABLE = "Currently no courses are available to be added";
    public static final String SAVED_SUCCESFULLY = "changes saved successfully";
    public static final String SUCCESFULL_ADDITION = "User successfully added";
    public static final String CLASS_FULL = "Class is full!";
    public static final String REPETITION_ERROR = "Two courses can't be taken twice!";
    public static final String INELIGIBLE_ERROR = "You are ineligible for the course. Please complete the pre requisites.";
    public static final String PASS_CHANGE_SUCCESS = "Password changed successfully";


    public static final String ALREADY_GRADED_SECN = "This section has already been graded!";
    public static final String SELECT_CSV = "Selected file must be of .csv extension";
    public static final String EMPTY_CSV = "Inputted file is empty!";
    public static final String INVALID_COL = "CSV file has invalid number of columns";
    public static final String EMPTY_LINE = "User id is empty at line ";
    public static final String INV_SEM_ENTRY = "Invalid semester entry in csv file. Must be out of Semester 1,Semester 2,Semester 3";
    public static final String DUPLICATE_ENTRY = "Duplicaate userid  value detected";
    public static final String SHOOT_BOUND = "Marks must be 0-100";
    public static final String CSV_READ_ERR="Error in reading the CSV";


    public static final String ADMN_MAINT_MSG="Maintainence mode is on!";
    public static final String INST_MAINT_MSG="Maintainence mode is on! You cannot upload grades or file grievances";
    public static final String STU_MAINT_MSG="Maintainence mode is on! You cannot view grades,add/drop courses at the moment!";

    public static final String INVALID_COURS_ID="Invalid course or instructor id entered";

    public static final String NEGATIVE_VAL_ERROR="Value of numeric fields cannot be negative";
}

