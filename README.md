# FBMessagingBot

A Java desktop application for handling Facebook Marketplace inquiries through browser automation, configurable response rules, contact logging, and email notifications.

The project connects a Swing operator dashboard with Selenium, SQLite, Excel workbooks, and external services. Its central workflow turns incoming listing inquiries into predefined replies and follow-up notifications for the responsible agent.

> **Repository status:** This README documents the checked-in implementation, not a verified, ready-to-run release. Dependency versions and the original `.env` are not included. Review the [security and privacy checklist](#security-and-privacy) before attempting to run it: the source contains a hardcoded API credential and recipient addresses, and the workflow can send real messages and email.

## Features

- **Desktop controls:** A Swing login screen and dashboard for Facebook login, listing-response management, keyword rules, start/stop controls, and sleep-window settings.
- **Inquiry processing:** Selenium navigates Facebook's mobile web interface, loads unread conversations, and associates Marketplace inquiries with configured listing records.
- **Rule-based replies:** Listing-specific questions, keyword combinations, and fallback rules select predefined responses stored in SQLite or Excel.
- **Contact extraction:** A Wit.ai request attempts to identify names, email addresses, and phone numbers in customer messages. Response templates can include agent and property details.
- **Follow-up notifications:** JavaMail sends HTML email to the configured agent; a separate fallback path handles selected questions through fixed escalation recipients.
- **Message history:** Apache POI appends the conversation name and message text to an Excel workbook for follow-up.

This is **browser-driven workflow automation**, not an official Messenger API integration. Wit.ai is used for entity extraction; replies come from rules and templates, not a generative language model.

## Main Workflow

```text
Swing dashboard
    |
    v
Selenium: open unread Marketplace conversations
    |
    v
Match listing record and inspect conversation text
    |
    +-- Wit.ai: attempt contact extraction
    +-- SQLite: listing and keyword rules
    +-- Excel: predefined response pools
    |
    v
Select and send a reply
    |
    +-- Contact-handling paths: append name/message to Excel
    +-- Notification paths: send agent or escalation email
```

The worker repeats with a randomized 10–20 minute delay between cycles. It also invokes a home-feed browsing helper before checking the sleep window, so the sleep setting should not be treated as a guarantee of zero browser activity. Additional like/comment/video helpers exist in the source but are not the core inquiry-handling workflow described above.

## Code Guide

| Area | Source | Responsibility |
| --- | --- | --- |
| Entry point | [`src/Start.java`](src/Start.java) | Logging setup and Swing startup |
| Operator interface | [`src/ui/`](src/ui/) | Login, dashboard, listing responses, and keyword forms |
| Browser workflow | [`src/models/Automate.java`](src/models/Automate.java) | Navigation, conversation inspection, response selection, and sending |
| Browser setup | [`src/models/apis/ChromeDriverHelper.java`](src/models/apis/ChromeDriverHelper.java) | ChromeDriver configuration |
| Email delivery | [`src/models/apis/Email.java`](src/models/apis/Email.java) | Gmail SMTP integration |
| Rule storage | [`src/storages/DBHelper.java`](src/storages/DBHelper.java) | JDBC operations for listing and keyword records |
| Workbooks and templates | [`src/storages/ExcelHelper.java`](src/storages/ExcelHelper.java), [`src/storages/BotAnswers.java`](src/storages/BotAnswers.java) | Response pools, email templates, and message history |
| Supporting utilities | [`src/util/`](src/util/), [`src/models/ThreadPool.java`](src/models/ThreadPool.java) | Environment loading, entity extraction, timing, logging, and worker execution |

## Technology and Dependencies

The [original development notes](devInfo/dev_info.md) identify IntelliJ IDEA 2022.2.1, Java 1.8.0_241, and Swing. This is historical environment information, not a verified compatibility matrix.

The source uses these library families:

- Selenium Java and ChromeDriver
- SQLite JDBC
- Apache POI with XLSX support
- `java-dotenv` (`io.github.cdimascio.dotenv`)
- JavaMail using the `javax.mail` namespace
- Apache HttpClient's Fluent API (`org.apache.http.client.fluent`)
- Log4j 2, with SLF4J and Commons Logging references also present

No Maven or Gradle build definition, dependency lockfile, or dependency JARs are checked in. Compatible versions and transitive dependencies must be reconstructed and pinned before a reproducible build can be documented. The bundled Windows `driver/chromedriver.exe` is not evidence of compatibility with an installed browser or another operating system.

## Configuration

[`EnvHelper`](src/util/EnvHelper.java) loads configuration through `Dotenv.load()` and rejects missing or empty values. Keep the local `.env` out of version control; it is already ignored.

| Purpose | Environment keys |
| --- | --- |
| Local application login | `APP_EMAIL`, `APP_PASSWORD` |
| WebDriver system property and executable | `WEB_DRIVER_ID`, `WEB_DRIVER_PATH` |
| JDBC driver and connection URL | `JDBC_CLASSNAME`, `DB_URL` |
| Message history and response workbooks | `CONTACTS_EXCEL_FILE_NAME`, `ANSWERS_EXCEL_FILE_NAME` |
| SMTP authentication | `GMAIL_EMAIL`, `GMAIL_PASSWORD` |
| Shared keyword-rule scope | `FOR_ALL_PRODUCTS` |

The application login is separate from the Facebook credentials entered in the dashboard. Email delivery currently targets Gmail SMTP on port 465 with SSL.

<details>
<summary>Browser URL and selector keys referenced by the source</summary>

These values depend on the web interface and are not supplied by this repository. Some locators are also hardcoded in Java, so updating `.env` alone may not restore compatibility.

**Login and session checks**

- `FACEBOOK_MOBILE_LINK`
- `ENGLISH_ELEMENT_XPATH`
- `LOGIN_FAIL_ELEMENT_XPATH`
- `INCORRECT_PASS_ELEMENT_XPATH`
- `EMAIL_ELEMENT_NAME`
- `PASSWORD_ELEMENT_NAME`
- `LOGIN_BTN_NAME`
- `LOGIN_CODE_ELEMENT_NAME`
- `LOGIN_STATUS_CHECK_ELEMENTS_XPATH`

**Inquiry processing and the main loop**

- `FACEBOOK_MESSAGES_LINK`
- `LOAD_MORE_MESSAGE_ELEMENT_XPATH`
- `MARKETPLACE_ITEM_ANCHOR_XPATH`
- `UNREAD_MESSAGE_ELEMENTS_XPATH`
- `LOAD_MORE_1_TO_1_MESSAGES_ELEMENT_XPATH`
- `ONE_TO_ONE_MESSAGES_ELEMENTS_XPATH`
- `RANDOM_HOME_ANCHOR_ELEMENTS_XPATH`

**Additional browser helpers**

- `LIKE_ELEMENTS_XPATH`
- `FACEBOOK_VIDEO_LINK`
- `COMMENT_ELEMENTS_XPATH`
- `COMMENT_INPUT_ELEMENT_ID`
- `COMMENT_POST_ELEMENT_XPATH`

</details>

Wit.ai authentication is currently hardcoded in `Util.fetchDetails`; there is **no implemented environment-variable setting for that token**. Moving it to secret configuration requires a code change.

## Data and Templates

The following contracts come from the source, not from verification that the bundled data is safe or complete:

- **SQLite:** Queries expect `product` and `keyword` tables. Listing records supply question/response text, agent details, and a property subject; keyword records include single, AND, and OR matching fields. No database migration or schema-initialization workflow is provided.
- **Response workbook:** `ANSWERS_EXCEL_FILE_NAME` must contain an `AnswerPool` sheet. Columns **B**, **C**, and **D** hold phone-number, email-address, and question-mark response categories respectively. Excel row **2** contains numeric response counts; row **3** onward contains the response strings.
- **Message-history workbook:** `CONTACTS_EXCEL_FILE_NAME` must contain `Sheet1`. New rows contain a name in column **A** and message text in column **B**, rather than a normalized contact database.
- **Email templates:** [`answers/answers.properties`](answers/answers.properties) supplies `mail.toAgent` and `mail.toMaintenance`. Preserve the formatting placeholders expected by the calling code.
- **Logging:** Startup references `log/log4j2.properties`, which is not included in the repository.

Tracked SQLite and Excel files must not be assumed to be sanitized sample data. Use reviewed, synthetic fixtures when reconstructing the environment.

## Reconstructing a Development Environment

1. Complete the security review below before using any real account, recipient, or customer data.
2. Import the Java sources into an IDE, mark `src` as the source root, and resolve compatible versions of the listed dependencies. Record them in a build definition.
3. Prepare sanitized SQLite and Excel fixtures that satisfy the source contracts, and review the email templates.
4. Create a local `.env` with the required configuration and verified selectors. Configure a browser driver appropriate for the chosen browser and operating system, and supply the missing logging configuration.
5. Replace the exposed credential and fixed recipients in code, and introduce a dry-run or mocked transport for browser sends, email, and entity extraction before testing the workflow.
6. After those safeguards are in place, use the default-package `Start` class as the application entry point, with the repository root as the working directory for relative resources.

These are reconstruction steps, not a tested quick-start. Compilation and live integrations have not been validated for this README, and the current application does not provide a built-in dry-run mode.

## Security and Privacy

- **Exposed credential:** `Util.fetchDetails` contains a hardcoded Wit.ai bearer token. Treat it as compromised, revoke/rotate it, and externalize the replacement. Removing it from the current source alone does not invalidate an exposed credential or remove it from Git history.
- **Real recipients:** The escalation path in `Automate.CheckHardToReplyMessage` contains fixed email recipients. Replace them with explicitly approved test recipients before any execution.
- **External data transfer:** Customer message text is sent to Wit.ai as a request parameter. Messages and contact details can also reach workbooks, email, and logs. Review authorization, retention, and access controls before processing real data.
- **Transport and logs:** Chrome options include `--ignore-certificate-errors`, and JavaMail debug output is enabled. Review these settings; do not carry certificate-check bypasses or verbose sensitive logging into a deployed environment.
- **Local login:** The Swing login uses simple environment-value comparisons, including case-insensitive password matching. It is not a hardened authentication boundary.
- **Account use:** Browser automation can send real Facebook messages. Use only authorized accounts and workflows, and review applicable platform rules before enabling automation. No current platform compatibility or policy compliance is claimed here.

## Limitations and Improvement Areas

- **DOM dependence:** Facebook page structure and selectors are external dependencies; current compatibility is unverified.
- **Response parsing:** Wit.ai output is parsed using string splitting rather than a structured JSON parser. Response-shape changes or unexpected input can break extraction.
- **Rule correctness:** Keyword precedence, SQL grouping, listing scope, and template substitution need focused tests. Previous-answer detection uses conversation-text heuristics rather than durable message IDs and idempotency records.
- **Worker lifecycle:** Start/stop behavior uses shared flags and executor tasks. Cancellation, shutdown, and Swing event-dispatch-thread boundaries need review; the Stop button is not a guaranteed immediate halt.
- **Persistence:** Workbook operations share mutable state and do not establish a concurrency-safe storage layer.
- **Verification:** A pinned build, automated test suite, mocked integrations, and sanitized fixtures would make behavior easier to reproduce and maintain.

## 한국어 요약

Facebook Marketplace 문의 대응을 자동화하는 Java Swing 데스크톱 애플리케이션입니다. Selenium으로 대화 내용을 확인하고, SQLite의 매물·키워드 규칙과 Excel 응답 템플릿을 이용해 답변합니다. Wit.ai를 통한 연락처 추출, 담당자 이메일 알림, Excel 메시지 기록까지 연결한 업무 자동화 프로젝트입니다.

공식 Messenger API나 생성형 챗봇이 아닌 **브라우저 기반의 규칙·템플릿 자동화**입니다. 현재 저장소에는 의존성 버전과 실행 설정이 빠져 있어 환경 복원이 필요하며, 노출된 API 토큰과 고정 수신처를 정리하고 테스트용 전송 환경을 마련하기 전에는 실제 계정으로 실행하면 안 됩니다.

## License

Source code is provided under the [MIT License](LICENSE). Third-party services, libraries, and any included data remain subject to their respective terms and permissions.
