import { FilterType } from "./filter-type";

enum Values {
  MANY_PIECE = "many_piece",
  CONTAINER = "container",
  PROJECTILE_WEAPON = "projectile_weapon",
  THROWING_WEAPON = "throwing_weapon",
  MELEE_WEAPON = "MELEE_WEAPON"
}

export class ItemType extends FilterType {

  static readonly values = Values;
  value: string | undefined = undefined;
  getType(): string {
    return "ItemType";
  }
  getValues(): any {
    return ItemType.values;
  }

  getValue(): string | number | undefined {
    return this.value;
  }
}

