export abstract class FilterType {
  abstract getValues(): any;
  abstract getType(): string;
  abstract getValue(): string | number | undefined ;
}
