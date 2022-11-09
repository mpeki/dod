import { Page } from "./page";

export interface PageResult<T> {
  content: T[];
  page: Page;
}