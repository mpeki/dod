/*
export enum Value {
  KNOWLEDGE = "knowledge", COMBAT = "combat", COMMUNICATION = "communication", THIEVING = "thieving", PERCEPTION = "perception", OUTDOOR = "outdoor"
}

enum Filter {
  ALL = "all"
}

export const Group = { ...Value };
export const GroupFilter = { ...Filter, ...Value };
*/


import { FilterType } from "./filter-type";

enum Values {
  KNOWLEDGE = "knowledge",
  COMBAT = "combat",
  COMMUNICATION = "communication",
  THIEVING = "thieving",
  PERCEPTION = "perception",
  OUTDOOR = "outdoor"
}

export class Group extends FilterType {

  static readonly values = Values;
  value: string | undefined = undefined;
  getType(): string {
    return "Group";
  }
  getValues(): any {
    return Group.values;
  }

  getValue(): string | number | undefined {
    return this.value;
  }
}

