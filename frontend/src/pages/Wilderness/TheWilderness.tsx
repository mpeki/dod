import { Typography } from "@mui/material";
import Stack from "@mui/material/Stack";
import { WildernessImageMenu } from "./WildernessImageMenu";

export const TheWilderness = () =>
  <Stack direction={"row"}>
    <Stack direction={"column"}>
      <Typography sx={{ p: 2 }} variant="h5" gutterBottom={false} align={"justify"}>
        The Wilderness
      </Typography>
      <Typography sx={{ p: 2 }} variant="body2" gutterBottom={false} align={"justify"}>
        Beyond the towering city walls, where the din of merchants and chatter of townsfolk faded to whispers, the
        adventurer found a world untouched by stone and mortar. The wilds beckoned, a vast expanse of unpredictable
        beauty and danger.
      </Typography>
      <Typography sx={{ p: 2 }} variant="body2" gutterBottom={false} align={"justify"}>
        Forests rose up with ancient trees, their bark gnarled and twisted from centuries of standing guard. Each
        leaf, every branch seemed to hum with a deep, ancient magic, telling tales of old for those who cared to
        listen. Hidden glades, bathed in dappled sunlight, concealed babbling brooks and serene ponds, their
        surfaces mirroring the ever-changing heavens.
      </Typography>
      <Typography sx={{ p: 2 }} variant="body2" gutterBottom={false} align={"justify"}>
        But the wilds were not just a place of beauty; they were alive, and with life came unpredictability.
        Creatures roamed these lands, some majestic in their grace, like the elusive white stag or the soaring
        griffin. Others, however, lurked in the shadows, their intentions less clear, their presence hinted at by
        the rustling of leaves or a distant growl.
      </Typography>
      <WildernessImageMenu />
    </Stack>
  </Stack>;
