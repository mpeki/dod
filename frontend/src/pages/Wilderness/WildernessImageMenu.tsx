import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import IconButton from "@mui/material/IconButton";
import KeyboardDoubleArrowRightIcon from "@mui/icons-material/KeyboardDoubleArrowRight";
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import castleRuinImg from "../../img/castle_ruin.png";
import woodsImg from "../../img/old_forest.png";
import frozenLandsImg from "../../img/frozen_lands.png";
import caveImg from "../../img/the_cave.png";
import { Link } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import Tooltip from '@mui/material/Tooltip';

export const WildernessImageMenu = () => {

  const auth = useAuth();

  const itemData = [
    {
      img: woodsImg,
      title: "The Old Forrest",
      description: "Venture into the old forrest",
      page: "/home",
      loginRequired: true
    },
    {
      img: frozenLandsImg,
      title: "The Frozen Lands",
      description: "Hunt the furries",
      loginRequired: true,
      page: "/home"
    },
    {
      img: caveImg,
      title: "The Cave",
      description: "Explore the cave",
      loginRequired: true,
      page: "/home"
    },
    {
      img: castleRuinImg,
      title: "The Old Castle Ruins",
      description: "Search for treasure",
      loginRequired: true,
      page: "/home"
    }
  ];

  return (
    <ImageList sx={{ width: "100%", height: 300 }} cols={4} gap={5}>
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