import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import IconButton from "@mui/material/IconButton";
import KeyboardDoubleArrowRightIcon from "@mui/icons-material/KeyboardDoubleArrowRight";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import ruleImg from "../img/dod-logo_small.png";
import combatImg from "../img/combat.png";
import charCreateImg from "../img/new_character.png";
import theCityImg from "../img/the_city.png";
import theWildImg from "../img/the_wild.png";
import settingsImg from "../img/settings.png";
import dmImg from "../img/dm.png";
import signupImg from "../img/signup.png";
import { Link } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import Tooltip from "@mui/material/Tooltip";
import { useTranslation } from "react-i18next";
import i18next from 'i18next';

export const HomeImageMenu = () => {
  const { t } = useTranslation("home");
  const auth = useAuth();

  const itemData = [
    {
      img: ruleImg,
      title: i18next.t("home:homeImgMenu.rules.title"),
      description: i18next.t("home:homeImgMenu.rules.description"),
      loginRequired: false
      // page: "/characters"
    },
    {
      img: charCreateImg,
      title: t("homeImgMenu.charCreation.title"),
      description: t("homeImgMenu.charCreation.description"),
      page: "/characters",
      loginRequired: true
    },
    {
      img: theCityImg,
      title: t("homeImgMenu.city.title"),
      description: t("homeImgMenu.city.description"),
      page: "/city",
      loginRequired: true
    },
    {
      img: theWildImg,
      title: t("homeImgMenu.wilderness.title"),
      description: t("homeImgMenu.wilderness.description"),
      page: "/wilderness",
      loginRequired: true
    },
    {
      img: dmImg,
      title: t("homeImgMenu.dm.title"),
      description: t("homeImgMenu.dm.description"),
      loginRequired: true
    },
    {
      img: combatImg,
      title: t("homeImgMenu.combat.title"),
      description: t("homeImgMenu.combat.description"),
      loginRequired: true
    },
    {
      img: signupImg,
      title: t("homeImgMenu.signup.title"),
      description: t("homeImgMenu.signup.description"),
      loginRequired: false
    },
    {
      img: settingsImg,
      title: (auth.isAuthenticated ? t("homeImgMenu.aboutSettings.title") : t("homeImgMenu.about.title")),
      page: "/settings",
      description: (auth.isAuthenticated ? t("homeImgMenu.aboutSettings.description") : t("homeImgMenu.about.description")),
      loginRequired: false
    }
  ];

  return (
    <ImageList sx={{ width: "100%", height: 600 }} cols={4} gap={5}>
      {itemData.map((item) => (
        <ImageListItem key={item.img}>
          <img
            src={item.img}
            srcSet={item.img}
            alt={item.title}
            loading="lazy"
          />
          <Link style={{ pointerEvents: !item.loginRequired || auth.isAuthenticated ? "auto" : "none" }}
                to={item.page || "/"}
                onClick={item.page === "signin" ? () => void auth.signinRedirect() : () => undefined}>
            <ImageListItemBar
              title={item.title}
              subtitle={item.description}
              actionIcon={
                <IconButton
                  sx={{ color: "rgba(255, 255, 255, 0.54)" }}
                  aria-label={`info about ${item.title}`}
                >
                  {(!item.loginRequired || auth.isAuthenticated) ? <KeyboardDoubleArrowRightIcon /> :
                    <Tooltip title="Login"><LockOutlinedIcon /></Tooltip>}
                </IconButton>
              }
            />
          </Link>
        </ImageListItem>
      ))}
    </ImageList>
  );
};