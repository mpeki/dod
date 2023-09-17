import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

function useManageCharacters() {
  const navigate = useNavigate();
  useEffect(() => {
    navigate("/characters");
  }, [navigate]);
}

export default useManageCharacters;
