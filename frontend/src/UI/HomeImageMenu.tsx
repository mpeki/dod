import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import IconButton from "@mui/material/IconButton";
import KeyboardDoubleArrowRightIcon from "@mui/icons-material/KeyboardDoubleArrowRight";
import ruleImg from "../img/dod-logo_small.png";
import combatImg from "../img/combat.png";
import charCreateImg from "../img/new_character.png";
import storeImg from "../img/general_store.png";
import smithyImg from "../img/the_smithy.png";
import { Link } from "react-router-dom";

export const HomeImageMenu = () => {

  const itemData = [
    {
      img: ruleImg,
      title: "Game Rules",
      description: "Read them and weep",
      // page: "/characters"
    },
    {
      img: charCreateImg,
      title: "Character Creation",
      description: "View and create characters",
      page: "/characters"
    },
    {
      img: combatImg,
      title: "Combat",
      description: "Engage in combat with other players"
    },
    {
      img: storeImg,
      title: "The Store",
      description: "Buy and sell items"
    },
    {
      img: smithyImg,
      title: "The Smithy",
      description: "Craft and upgrade items"
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
          <Link to={item.page || "/"}>
            <ImageListItemBar
              title={item.title}
              subtitle={item.description}
              actionIcon={
                <IconButton
                  sx={{ color: "rgba(255, 255, 255, 0.54)" }}
                  aria-label={`info about ${item.title}`}
                >
                  <KeyboardDoubleArrowRightIcon />
                </IconButton>
              }
            />
          </Link>
        </ImageListItem>
      ))}
    </ImageList>
  );
};