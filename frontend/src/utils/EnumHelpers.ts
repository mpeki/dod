import { FilterType } from "../types/filter-type";

export class EnumHelpers {

  static isInstance<T extends object>(value: string, type: T): type is T {
    return Object.values(type).includes(value)
  }

  static getNamesAndValues<T extends number | string>(e: any) {
    return EnumHelpers.getNames(e).map(n => ({ name: n, value: e[n] as T }));
  }

  static getLabelAndValues<T extends FilterType>(e: FilterType) {
    const names: string[] = EnumHelpers.getNames(e.getValues());
    return names.map(n => ({ label: n, value: n, type: (e as T).getType() }))
  }


  static getNames(e: any) {
    return EnumHelpers.getObjValues(e).filter(v => typeof v === 'string') as string[];
  }

  static getValues<T extends number | string>(e: any) {
    return EnumHelpers.getObjValues(e).filter(v => typeof v === 'number') as T[];
  }

  static getSelectList<T extends number, U>(e: any, stringConverter: (arg: U) => string) {
    const selectList = new Map<T, string>();
    this.getValues(e).forEach(val => selectList.set(val as T, stringConverter(val as unknown as U)));
    return selectList;
  }

  static getSelectListAsArray<T extends number, U>(e: any, stringConverter: (arg: U) => string) {
    return Array.from(this.getSelectList(e, stringConverter), value => ({ value: value[0] as T, presentation: value[1] }));
  }

  private static getObjValues(e: any): (number | string)[] {
    return Object.keys(e).map(k => e[k]);
  }


}