import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import IconButton from "@mui/material/IconButton";
import KeyboardDoubleArrowRightIcon from "@mui/icons-material/KeyboardDoubleArrowRight";
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import ruleImg from "../img/dod-logo_small.png";
import combatImg from "../img/combat.png";
import charCreateImg from "../img/new_character.png";
import storeImg from "../img/general_store.png";
import smithyImg from "../img/the_smithy.png";
import signupImg from "../img/signup.png";
import { Link } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import Tooltip from '@mui/material/Tooltip';

export const HomeImageMenu = () => {

  const auth = useAuth();

  const itemData = [
    {
      img: ruleImg,
      title: "Game Rules",
      description: "Read them and weep",
      loginRequired: false
      // page: "/characters"
    },
    {
      img: charCreateImg,
      title: "Character Creation",
      description: "View and create characters",
      page: "/characters",
      loginRequired: true
    },
    {
      img: combatImg,
      title: "Combat",
      description: "Engage in combat with other players",
      loginRequired: true
    },
    {
      img: storeImg,
      title: "The Store",
      description: "Buy and sell items",
      loginRequired: true
    },
    {
      img: smithyImg,
      title: "The Smithy",
      description: "Craft and upgrade items",
      loginRequired: true
    },
    {
      img: signupImg,
      title: "Sign in or sigup",
      page: "signin",
      description: "The goblins want you!",
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
          <Link style={{pointerEvents: !item.loginRequired || auth.isAuthenticated ? 'auto' : 'none'}} to={item.page || "/"} onClick={item.page === 'signin' ? () => void auth.signinRedirect() : () => undefined}>
            <ImageListItemBar
              title={item.title}
              subtitle={item.description}
              actionIcon={
                <IconButton
                  sx={{ color: "rgba(255, 255, 255, 0.54)" }}
                  aria-label={`info about ${item.title}`}
                >
                  {(!item.loginRequired || auth.isAuthenticated) ? <KeyboardDoubleArrowRightIcon /> : <Tooltip title="Login"><LockOutlinedIcon/></Tooltip>}
                </IconButton>
              }
            />
          </Link>
        </ImageListItem>
      ))}
    </ImageList>
  );
};