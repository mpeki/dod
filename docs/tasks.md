# Improvement Tasks

1. [ ] Establish continuous integration pipeline to run backend and frontend tests on each commit.
2. [ ] Expand architecture documentation beyond the existing entity diagram.
3. [ ] Provide OpenAPI/Swagger documentation for backend REST endpoints.
4. [ ] Implement global exception handling to standardize API error responses.
5. [ ] Apply validation annotations across DTOs and controllers to enforce input constraints.
6. [ ] Replace `System.out.println` statements with structured logging using SLF4J.
7. [ ] Refactor `Dice` utility to reuse a single `SecureRandom` instance and remove debug output.
8. [ ] Document or remove empty constructors such as in `SkillConverter`.
9. [ ] Add integration tests for core domain services (e.g., combat, items, change requests).
10. [ ] Add unit tests for utility classes (`Dice`, `SkillConverter`) to improve coverage.
11. [ ] Consolidate frontend API calls within a dedicated service layer and handle errors gracefully.
12. [ ] Introduce global state management (e.g., React Context or Redux) to simplify complex UI flows.
13. [ ] Increase frontend test coverage for pages and services.
