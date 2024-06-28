import { useContext, useState } from "react";
import { createContext } from "react";
import Layout from "./Layout";

const LayoutContext = createContext();

export const useLayout = () => useContext(LayoutContext);

export const LayoutProvider = ({ children }) => {
  const [extraElement, setExtraElement] = useState(null);

  return (
    <LayoutContext.Provider value={{ setExtraElement }}>
      <Layout extraElement={extraElement}>{children}</Layout>
    </LayoutContext.Provider>
  );
};
