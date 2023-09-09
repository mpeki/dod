import axios, { AxiosResponse } from "axios";
import { template, templateSettings } from "lodash";

export interface UIOptions {
  contextPath: string;
  gameApiUri: string;
  authRedirectUri: string;
}

export let config: UIOptions = {
  contextPath: "",
  gameApiUri: "",
  authRedirectUri: ""
};

/**
 * Will register configurable env variables and make them available by importing the config object from this file
 * From here variables can be requested as config.variableName
 *
 * In production:
 *    Will fetch variables from json file from public/environment/environment.json
 *    The json should be compliant with the UIOptions interface definition above
 *
 * In development:
 *    Will read from .env.development file and attach the associated variables
 *
 * NB: register() is asynchronous, and will have to be
 * awaited before mounting the root component to the DOM
 */
export const registerConfig = async (): Promise<void> => {
  console.log("Registering configuration...")
  if (process.env.NODE_ENV === "production") {
    console.log(`Reading production configuration from ${process.env.PUBLIC_URL}/env/environment.json ...`);
    const optionsJson: AxiosResponse = await axios.get(`${process.env.PUBLIC_URL}/env/environment.json`);
    config = await interpolate(optionsJson);
    console.log("Production configuration loaded.");
    console.log(config);
  } else {
    console.log("Reading development configuration from .env.* ...");
    const dateFormatOrder = process.env.REACT_APP_DATE_FORMAT_ORDER
      ? process.env.REACT_APP_DATE_FORMAT_ORDER.split(",")
      : ["D", "D", "M", "M", "Y", "Y", "Y", "Y"];

    config = {
      contextPath: process.env.REACT_APP_CONTEXT_PATH || "",
      gameApiUri: process.env.REACT_APP_GAME_API_URI || "",
      authRedirectUri: process.env.REACT_APP_AUTH_REDIRECT_URI || ""
    };
  }
  console.log(`Application configuration: ${JSON.stringify(config)}`);
};

async function interpolate(optionsJson: AxiosResponse): Promise<UIOptions> {
  const data = optionsJson.data;
  let optionsInterpolated;

  templateSettings.interpolate = /\${([\s\S]+?)}/g;
  try {
    const compiled = template(JSON.stringify(data));

    optionsInterpolated = JSON.parse(compiled(data));
  } catch (error) {
    console.error("Interpolation of environment.json file failed, check file syntax. " + error);
  }

  return optionsInterpolated;
}
