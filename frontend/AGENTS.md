# AGENTS.md

Guidance for automated tools (CI, build bots, Dependabot/Renovate, container builders, AI code agents) that interact with this **React frontend** module.

---

## 1 Overview

* **Stack** : React 18, TypeScript, Vite/CRA build (via `react‑scripts 5.0.1`).
* **Purpose** : Static SPA served by NGINX inside Docker; consumed by the main platform at `/ui`.

## 2 Prerequisites

| Tool | Required Version                     | Notes                                                 |
| ---- | ------------------------------------ | ----------------------------------------------------- |
| Node | **≥ 24.3.0** (LTS 22 also passes CI) | Declared in `package.json > engines.node`.            |
| npm  | **≥ 11.4.2**                         | Bundled with Node 24; lockfile created with npm `ci`. |

## 3 Local commands

```bash
npm ci          # install from lockfile
npm start       # dev server (http://localhost:3000)
npm run build   # production build → ./build
npm test        # Jest + React Testing Library
npm run lint    # ESLint + Prettier
```

All scripts are defined under `package.json > scripts`.

## 4 Docker build (multi‑stage)

```dockerfile
# build
ARG NODE_VERSION=24.3.0
FROM node:${NODE_VERSION}-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# runtime
FROM nginx:1.27.1-alpine
COPY --from=builder /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx","-g","daemon off;"]
```

Use `docker build -t frontend-ui .`.

## 5 Branch & PR conventions

* **main** = deploy branch (auto‑built image tag `latest`).
* Feature branches: `feat/<topic>`  → PR → squash‑merge.
* Dependabot & Renovate PRs must pass GitHub Actions before merge.

## 6 CI pipeline (GitHub Actions)

1. Node setup (`actions/setup-node` using `package.json > engines.node`).
2. `npm ci && npm run lint && npm test --ci`.
3. Build & push Docker image on merge to **main**.

## 7 Directory structure (top‑level)

```
├── public/           # static assets root
├── src/              # React code
│   ├── components/
│   ├── hooks/
│   └── …
├── build/            # generated at runtime (ignored)
├── Dockerfile
└── nginx-custom.conf
```

## 8 Contacts / Ownership

| Area          | Slack / GitHub handle |
| ------------- | --------------------- |
| Frontend lead | @alice-dev            |
| Dev‑Ops       | @ops-max              |

---

*Document version : 2025‑07‑08*
