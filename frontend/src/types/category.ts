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
  constructor(value?: string) {
    super();
    this.value = value;
  }

  static readonly values = Values;
  value: string | undefined;

  getType(): string {
    return "Category";
  }
  getValues(): any {
    return Category.values
  }
  getValue(): string | number | undefined {
    return this.value;
  }

}
