import React, { useCallback, useEffect, useState } from "react";
import { useTranslation } from 'react-i18next';
import ReactFlagsSelect from "react-flags-select";
import classes from "./LanguageSwitcher.module.css";

const getBrowserLanguage = () => {
  const browserLang = navigator.language || navigator.languages[0];
  const lang = browserLang.split('-')[0]; // Get the language part (e.g., "en" from "en-US")
  return lang;
};

const LanguageSwitcher: React.FC = () => {
  const { i18n } = useTranslation();
  const defaultLanguage = (["en", "da"].includes(getBrowserLanguage()) ? getBrowserLanguage() : "en") === "en" ? "GB" : "DK";
  console.log("defaultLanguage", defaultLanguage)
  const [selected, setSelected] = useState(defaultLanguage);

  const changeLanguage = useCallback((lng: string) => {
    console.log("changeLanguage", lng);
    i18n.changeLanguage(lng).then();
  }, [i18n]);

  console.log(selected);
  useEffect(() => {
    changeLanguage(selected === 'GB' ? 'en' : 'da');
  }, [changeLanguage, selected]);

  return (
    <div className={classes.languageSwitcher}>
      <ReactFlagsSelect
        countries={["GB", "DK"]}
        showSelectedLabel={false}
        selected={selected}
        onSelect={(code) => { changeLanguage(code === 'GB' ? 'en' : 'da'); setSelected(code); }}
        selectedSize={14}
        fullWidth={false}
        showOptionLabel={false}
      />
    </div>
  );
}

export default LanguageSwitcher;
