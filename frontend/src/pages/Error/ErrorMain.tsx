import { Error401 } from "./Error401";
import { DefaultError } from "./DefaultError";
import { ErrorConnection } from "./ErrorConnection";

interface IProps {
  errorCode: number;
}

export const ErrorMain = ({ errorCode } : IProps ) => {

  type ErrorComponentType = React.FunctionComponent;

  const ERROR_COMPONENTS: {[key: number]: ErrorComponentType} = {
    1: ErrorConnection,
    401: Error401,
  };
  const ErrorComponent = ERROR_COMPONENTS[errorCode];

  if (!ErrorComponent) {
    // Optional: Render a default error component if errorCode is not found in the mapping
    return <DefaultError />;
  }

  return <ErrorComponent />;}