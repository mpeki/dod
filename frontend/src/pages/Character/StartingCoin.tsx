import { LinearProgressPropsColorOverrides } from "@mui/material";
import { Character } from "../../types/character";
import { OverridableStringUnion } from "@mui/types";
import { StatusLinearProgress } from "./status/StatusLinearProgress";


interface IProps {
  character: Character;
  color?: OverridableStringUnion<
    "primary" | "secondary" | "error" | "info" | "success" | "warning" | "inherit",
    LinearProgressPropsColorOverrides
  >;
}

export const StartingCoin = ({ character, color = "info" }: IProps) => {

  if(character && character.items) {
    const silver = character.items.silver ? character.items.silver.quantity : 0;
    // const normalise = (value: number) => ((value) * 100) / (value);
    const maxCoin : number = parseInt(localStorage.getItem(`${character.id}_startCap`) || "0");
    // const normalise = (value: number) => ((value - maxCoin) * 100) / (0 - maxCoin);

    return (
        <StatusLinearProgress label={"Starting Silver"}
                              maxValue={maxCoin}
                              currentValue={silver}
                              yellowThreshold={maxCoin - (maxCoin * .8)}
                              redThreshold={maxCoin - (maxCoin * .2)}
                              goodDirection={"empty"}/>
    );
  } else {
    return <>No Character loaded!</>
  }
};