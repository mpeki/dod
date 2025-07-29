DOD Improvement Tasks Checklist
Generated on: 2025-08-10 16:24 local

1. [ ] Establish a shared Java toolchain version across all modules (e.g., 21) and enforce via Gradle toolchains; update selenium-tests (currently Java 20) and root settings to use the same toolchain.
2. [ ] Centralize Keycloak dependency versions in gradle/libs.versions.toml and remove the hardcoded 26.3.2 from backend/build.gradle; align keycloak-core, admin-client, and test-helper to a single compatible version.
3. [ ] Use version catalogs for all dependencies (including backend keycloak-core) and handle GitHub Packages credentials for droolsassert gracefully (fallback or conditional repository when token is absent).
4. [ ] Enable Gradle dependency locking to stabilize transitive versions across environments.
5. [ ] Tighten Sonar + Jacoco integration: ensure jacoco.xml is generated and picked by Sonar, and reconsider exclusions (e.g., avoid broad org.drools.* exclusions where possible).
6. [ ] Enable Gradle configuration cache and build cache; document how to run repeatable builds locally and in CI.
7. [ ] Create a CI pipeline (e.g., GitHub Actions) running: backend unit/integration tests, frontend lint/tests, selenium-tests (hub profile), Jib image builds, and artifact uploads.
8. [ ] Configure Renovate/Dependabot for automated dependency updates bound to the version catalogs.

Security and configuration
9. [ ] Move docker-compose credentials and sensitive values to .env and/or Docker secrets; remove inline secrets.
10. [ ] Fix Keycloak healthcheck in docker-compose: healthcheck targets 9000 but container runs on 8181; align to 8181/health.
11. [ ] Enforce HTTPS and secure cookie/session settings in production; document TLS/reverse proxy approach and add placeholders in application-*.yml.
12. [ ] Standardize configuration profiles (default, dev, test, prod); audit SPRING_PROFILES_ACTIVE usage in docker-compose and test tasks.
13. [ ] Add OWASP Dependency Check or Snyk to CI for vulnerability scanning of JVM and Node dependencies.
14. [ ] Introduce global validation and input sanitization; add @ControllerAdvice for validation errors with RFC7807 responses.

Observability
15. [ ] Expose Actuator endpoints (health, info, metrics, prometheus); secure them and define readiness/liveness groups used by healthchecks.
16. [ ] Standardize structured logging (JSON) and correlation IDs (MDC); propagate request IDs across services and E2E tests.

Database and data
17. [ ] Update MySQL character set to utf8mb4 (docker-compose and schema) instead of utf8mb3; review collations.
18. [ ] Add indexes for frequently queried fields; verify via Hibernate statistics in tests.
19. [ ] Tune HikariCP and timeouts; document Hypersistence settings and lazy loading guidelines.

Backend architecture and code quality
20. [ ] Extract combat domain services from util classes (e.g., FightRules/MeleeCombatRules) to improve testability and separation of concerns; define interfaces.
21. [ ] Add comprehensive unit and property-based tests for fight rules (initiative, damage, defense) using JUnit 5 and droolsassert; add parameterized tests.
22. [ ] Replace magic strings with enums/constants (e.g., skill keys for items) and centralize mapping logic.
23. [ ] Introduce MapStruct for DTO/domain mapping or harden ModelMapper with strict matching; add mapper tests.
24. [ ] Add null-safety/defensive checks where values may be absent (e.g., primaryWeapon in doHit paths); prefer fail-fast with descriptive messages.
25. [ ] Implement domain validation for CharacterDTO/ItemDTO invariants; ensure CharacterItemDTO defaults are consistent and consider immutability for DTOs where feasible.
26. [ ] Consolidate error handling with a global exception handler producing problem+json; normalize error codes.
27. [ ] Move toward package-by-feature structure to reduce util-centric packages; split oversized classes.
28. [ ] Add JavaDoc/comments for public services and rules; generate API docs via Asciidoctor as part of the build.
29. [ ] Gradually raise coverage thresholds; restrict exclusions to generated or external code.

Rule engine and Drools
30. [ ] Add unit tests for DRL rules (e.g., AutoFightRules.drl) with droolsassert; ensure each rule has at least one positive and one negative test.
31. [ ] Externalize rule parameters (damage modifiers, difficulties) to configuration to avoid magic numbers; document defaults.
32. [ ] Centralize rule audit logging and explanations; ensure ActionResultDescription is consistently populated.

API and integration
33. [ ] Add springdoc-openapi and generate OpenAPI docs; publish alongside RestDocs snippets.
34. [ ] Add contract/integration tests with Testcontainers for Keycloak and MySQL; ensure deterministic startup (wait strategies) and cleanup.

Frontend
35. [ ] Modernize build toolchain: consider migrating from react-scripts to Vite (or upgrade to TS 5.x); ensure TypeScript is upgraded from 4.9 to 5.x.
36. [ ] Add ESLint + Prettier configs; remove accidental devDependency "install"; align Jest config with React Testing Library best practices.
37. [ ] Implement .env-based configuration for API base URLs; add axios interceptors for auth and error handling; add E2E mocks for local development.

Selenium tests
38. [ ] Stabilize E2E tests: add explicit waits and rerun strategy (rerunner-jupiter); capture screenshots/logs as CI artifacts.
39. [ ] Parameterize seleniumRunMode and grid URLs; support headless local runs; document docker-compose plugin stopContainers behavior.
40. [ ] Define tagging strategy (smoke/regression/critical) and wire tags to Gradle test properties.

Infrastructure and delivery
41. [ ] Pin Docker images to explicit versions/digests; align Selenium Hub/Node versions with test dependencies.
42. [ ] Add Gradle tasks or a Makefile for common flows (build, test, run, e2e, release).
43. [ ] Provide a local dev profile in docker-compose with hot reload and volume mounts for backend/frontend.
44. [ ] Add backup/restore scripts for the MySQL volume; harden MySQL config (auth plugins, secure options).

Documentation and developer experience
45. [ ] Enhance CONTRIBUTING.md with dev setup, test strategies, and commit conventions; align CHANGELOG with releases.
46. [ ] Add CODEOWNERS and PR templates; define branch protection and semantic versioning rules.
47. [ ] Introduce Architecture Decision Records (ADRs) and document key decisions (Drools, Keycloak, persistence choices).