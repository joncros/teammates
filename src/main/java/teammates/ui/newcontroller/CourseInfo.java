package teammates.ui.newcontroller;

import teammates.common.datatransfer.attributes.CourseAttributes;

/**
 * Data transfer objects for {@link CourseAttributes} between controller and HTTP.
 */
public class CourseInfo {

    /**
     * The output format of a course.
     */
    public static class CourseResponse extends ActionResult.ActionOutput {
        private final String courseId;
        private final String courseName;
        private final String timeZone;

        public CourseResponse(CourseAttributes courseAttributes) {
            this.courseId = courseAttributes.getId();
            this.courseName = courseAttributes.getName();
            this.timeZone = courseAttributes.getTimeZone().getId();
        }

        public String getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }
}
