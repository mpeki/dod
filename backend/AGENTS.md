# AGENTS.md – Spring Boot Service “Acme Orders”

This file tells ChatGPT Codex **how to behave like a disciplined teammate** in this repository.

---

## 1 Project snapshot
| Item                 | Value                                                    |
|----------------------|----------------------------------------------------------|
| Java version         | 21 (Temurin)                                             |
| Build tool           | Gradle Wrapper (`./gradlew`)                             |
| Spring Boot          | 3.5.x                                                    |
| Database             | MySql 8 (local via Testcontainers)                       |

---

## 2 Directory rules
| Path / glob                | What Codex may do                                |
|----------------------------|--------------------------------------------------|
| `java/**/data/**`          | ✅ Edit/create DTOs                               |
| `java/**/domain/**`        | ✅ Edit/create business logic, unit tests         |
| `java/**/system/**`        | ✅ Edit/create application config/security        |


In general, feel free to edit/create code or configuration files to this module. 

Changes always need to be submitted as a PR, which will be reviewed by a human before merging.

## PRs must meet the following criteria:
* Correct target branch
  * `develop` for new features, `main` for urgent fixes (security, etc.)
* Javadoc, Readme and relevant comments
  * Changes should be documented in the code and/or in the Readme.
* Appropriate use of logging
  * No `System.out.println` statements
* Appropriate test coverage
  * Test coverage must not drop below 80% (run `./gradlew test` to check)

---


[//]: # (## 3 Code style & conventions)

[//]: # (1. **Formatter** – use [Spring Java-Format] &#40;run with `./mvnw spring-javaformat:apply`&#41;.)

[//]: # (2. **Null-safety** – apply `@NonNull`/`@Nullable` annotations from `jakarta.annotation`.)

[//]: # (3. **Logging** – use `org.slf4j.Logger` via Lombok’s `@Slf4j`; no `System.out.println`.)

[//]: # (4. **Exceptions** – prefer custom subclasses of `ApiException` &#40;in `orders-api`&#41;.)

[//]: # (5. **Testing**)

[//]: # (    * Unit → JUnit 5 + AssertJ + Mockito.)

[//]: # (    * Integration → Spring Boot Test + Testcontainers.)

[//]: # (    * Coverage must stay **≥ 80 %** &#40;`./mvnw verify -Pcoverage`&#41;.)

---

## 4 How to build / test / run
These commands are run from the root of the repository, using the Gradle Wrapper (`./gradlew`) or docker compose.
| Purpose  | Command                                                   |
|----------|-----------------------------------------------------------|
| Clean build & run fast regression tests | `./gradlew clean :b:test` |
| Clean build & run integration tests | `./gradlew clean :b:test -DrunTestTags="integration"` |
| Launch app locally            | `docker compose up` |

> **Codex MUST execute the first command (`clean :b:test`) before proposing final code edits.**  
> If tests fail, it should fix or revert its changes.

---

## 5 Commit & PR policy
* **Conventional Commits** with scopes (`feat(api): …`, `fix(domain): …`).
* Squash-merge only; PR title becomes commit message.
* Each PR must pass **all** GitHub Actions checks (`build`, `coverage`, `e2e`).

Codex should include a “**Checklist**” comment in the PR body:

```markdown
- [ ] All unit/integration tests pass
- [ ] Coverage ≥ 80 %
