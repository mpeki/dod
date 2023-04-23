/*
enum Value {
  A = "A", B = "B"
}
enum Filter {
  ALL = "all"
}
export const Category = { ...Value };
export const CategoryFilter = { ...Filter, ...Value };*/


import { FilterType } from "./filter-type";

enum Values {
  A = "A", B = "B"
}

export class Category extends FilterType {
  static readonly values = Values;
  value: string | undefined = undefined;

  getType(): string {
    return "Category";
  }

  getValues(): any {
    return Category.values
  }

  getValue(): string | number | undefined {
    return undefined;
  }

}
