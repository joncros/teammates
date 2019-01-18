package teammates.test.cases.newaction;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import teammates.common.datatransfer.attributes.FeedbackSessionAttributes;
import teammates.common.exception.EntityNotFoundException;
import teammates.common.util.Const;
import teammates.ui.newcontroller.GetSessionResponseStatsAction;
import teammates.ui.newcontroller.GetSessionResponseStatsAction.FeedbackSessionStats;
import teammates.ui.newcontroller.JsonResult;

/**
 * SUT: {@link GetSessionResponseStatsAction}.
 */
public class GetSessionResponseStatsActionTest extends BaseActionTest<GetSessionResponseStatsAction> {

    @Override
    protected String getActionUri() {
        return Const.ResourceURIs.SESSIONS_STATS;
    }

    @Override
    protected String getRequestMethod() {
        return GET;
    }

    @Override
    @Test
    protected void testExecute() {

        // TODO login as instructor when the access control is supported
        loginAsAdmin();

        ______TS("typical: instructor accesses feedback stats of his/her course");

        FeedbackSessionAttributes accessibleFeedbackSession = typicalBundle.feedbackSessions.get("session1InCourse1");
        String[] submissionParams = new String[] {
                Const.ParamsNames.FEEDBACK_SESSION_NAME, accessibleFeedbackSession.getFeedbackSessionName(),
                Const.ParamsNames.COURSE_ID, accessibleFeedbackSession.getCourseId(),
        };

        GetSessionResponseStatsAction a = getAction(submissionParams);
        JsonResult r = getJsonResult(a);

        assertEquals(HttpStatus.SC_OK, r.getStatusCode());

        FeedbackSessionStats output = (FeedbackSessionStats) r.getOutput();
        assertEquals(10, output.getExpectedTotal());
        assertEquals(5, output.getSubmittedTotal());

        ______TS("fail: instructor accesses stats of non-existent feedback session");

        String nonexistentFeedbackSession = "nonexistentFeedbackSession";
        submissionParams = new String[] {
                Const.ParamsNames.FEEDBACK_SESSION_NAME, nonexistentFeedbackSession,
                Const.ParamsNames.COURSE_ID, accessibleFeedbackSession.getCourseId(),
        };

        a = getAction(submissionParams);
        GetSessionResponseStatsAction finalA = a;

        assertThrows(EntityNotFoundException.class, () -> getJsonResult(finalA));

    }

    @Override
    @Test
    protected void testAccessControl() {
        verifyOnlyAdminCanAccess();
    }

}
