package pl.piomin.sonar.plugin;

import org.junit.jupiter.api.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.java.checks.verifier.CheckVerifier;

public class CustomAuthorCommentCheckTest {

	@Test
	public void testOk() {
		CheckVerifier.newVerifier()
				.withCheck(new CustomAuthorCommentCheck())
				.addFiles(InputFile.Status.ADDED, "src/test/files/CustomAuthorCommentCheck.java")
				.verifyNoIssues();
	}
	
	@Test
	public void testFail() {
		CheckVerifier.newVerifier()
				.withCheck(new CustomAuthorCommentCheck())
				.addFiles(InputFile.Status.ADDED, "src/test/files/CustomAuthorCommentCheckFail.java")
				.verifyIssueOnFile("src/test/files/CustomAuthorCommentCheckFail.java");
	}
	
}
