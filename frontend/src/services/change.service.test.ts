import { Change } from "../types/change";
import { ChangeService } from "./change.service";


const changeData: Change = {
  changeDescription: "",
  changeKey: "",
  changeType: "",
  modifier: 10
}

describe( 'Change Service', () => {
  it('buy new skill',async () => {
    await ChangeService.doChange("2", changeData).then((data) => {
      expect(data).toBeTruthy();
    });
  });

});