import React from "react";
import ReactDOM from "react-dom";
import { AppMountParameters, CoreStart } from "../../../src/core/public";
import { AppPluginStartDependencies } from "./types";
import { opendistroNotificationsKibanaApp } from "./components/app";

export const renderApp = (
  { notifications, http }: CoreStart,
  { navigation }: AppPluginStartDependencies,
  { appBasePath, element }: AppMountParameters
) => {
  ReactDOM.render(
    <opendistroNotificationsKibanaApp
      basename={appBasePath}
      notifications={notifications}
      http={http}
      navigation={navigation}
    />,
    element
  );

  return () => ReactDOM.unmountComponentAtNode(element);
};
