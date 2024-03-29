import { Change, createChange } from "../types/change";
import { useChangeService } from "./change.service";

const changeData: Change = createChange("", "", "", 10);
describe( 'Change Service', () => {

  const { doChange } = useChangeService();

  it('buy new skill',async () => {
    await doChange("2", changeData).then((data) => {
      expect(data).toBeTruthy();
    });
  });

});