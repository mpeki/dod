import { MemoryRouter } from "react-router-dom";
import { StaticRouter } from "react-router-dom/server";

export const AppRouter = (props: { children?: React.ReactNode }) => {
  const { children } = props;
  if (typeof window === 'undefined') {
    return <StaticRouter location="/home">{children}</StaticRouter>;
  }

  return (
    <MemoryRouter initialEntries={['/home']} initialIndex={0}>
      {children}
    </MemoryRouter>
  );

}
