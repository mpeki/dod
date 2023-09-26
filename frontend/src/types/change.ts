import { Character } from "./character";
import { SecondaryChangeKey } from "./secondary-change-key";

export interface Change {
  changeType: string;
  changeDescription: string;
  changeKey: string;
  secondaryChangeKey?: SecondaryChangeKey;
  modifier: any;
  objectAfterChange?: Character;
  status: string;
  statusLabel: string;
}

// Function signatures
export function createChange(): Change;
export function createChange(changeType: string, changeDescription: string, changeKey: string, modifier: any): Change;
export function createChange(changeType: string, changeDescription: string, changeKey: string, secondaryChangeKey: SecondaryChangeKey | undefined, modifier: any): Change;

// Function implementation
export function createChange(
  changeType?: string,
  changeDescription?: string,
  changeKey?: string,
  secondaryChangeKeyOrModifier?: SecondaryChangeKey | any,
  modifier?: any
): Change {
  if (!changeType && !changeDescription && !changeKey && !secondaryChangeKeyOrModifier && !modifier) {
    return {
      changeType: 'NO_CHANGE',
      changeDescription: 'No Change',
      changeKey: '',
      secondaryChangeKey: undefined,
      modifier: {},
      status: 'PENDING',
      statusLabel: 'CHANGE_REQUEST_PENDING'
    }
  }

  let secondaryChangeKey: SecondaryChangeKey | undefined;
  if (!modifier && typeof secondaryChangeKeyOrModifier !== 'undefined') {
    modifier = secondaryChangeKeyOrModifier;
    secondaryChangeKey = undefined;
  } else {
    secondaryChangeKey = secondaryChangeKeyOrModifier as SecondaryChangeKey;
  }

  return {
    changeType: changeType || 'NO_CHANGE',
    changeDescription: changeDescription || 'No Change',
    changeKey: changeKey || '',
    secondaryChangeKey: secondaryChangeKey,
    modifier: modifier || {},
    status: 'PENDING',
    statusLabel: 'CHANGE_REQUEST_PENDING'
  };
}
