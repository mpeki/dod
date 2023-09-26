import { Typography } from "@mui/material";
import Stack from "@mui/material/Stack";
import { HomeImageMenu } from "../UI/HomeImageMenu";
import { useTranslation } from "react-i18next";
import { useAuth } from "react-oidc-context";

export const Home = () => {
  const { t } = useTranslation("home");
  const auth = useAuth();

  return (
    <Stack direction={"row"}>
      <Stack direction={"column"}>
        <Typography sx={{ p: 2 }} variant="h5" gutterBottom align={"justify"}>
          {t("title")}
        </Typography>
        {!auth.isAuthenticated && (
          <>
            <Typography sx={{ p: 2 }} variant="body2" gutterBottom align={"justify"}>
              {t("bodyText.part1")}
            </Typography>
            <Typography sx={{ p: 2 }} variant="body2" gutterBottom align={"justify"}>
              {t("bodyText.part2")}
            </Typography>
          </>
        )}
        <Typography sx={{ p: 2 }} variant="body2" gutterBottom align={"justify"}>
          {t("bodyText.part3")}
          <br />- 🐉&😈 🤘
        </Typography>
        <HomeImageMenu />
      </Stack>
    </Stack>
  );
};
