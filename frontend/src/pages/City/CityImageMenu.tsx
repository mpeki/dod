import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import IconButton from "@mui/material/IconButton";
import KeyboardDoubleArrowRightIcon from "@mui/icons-material/KeyboardDoubleArrowRight";
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import storeImg from "../../img/general_store.png";
import smithyImg from "../../img/the_smithy.png";
import { Link } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import Tooltip from '@mui/material/Tooltip';

export const CityImageMenu = () => {

  const auth = useAuth();

  const itemData = [
    {
      img: storeImg,
      title: "The General Store",
      description: "Buy and sell common items",
      loginRequired: true,
      page: "/home"
    },
    {
      img: smithyImg,
      title: "The Smithy",
      description: "Buy and sell weapons and armor",
      page: "/home",
      loginRequired: true
    }
  ];

  return (
    <ImageList sx={{ width: "100%", height: 600 }} cols={2} gap={5}>
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