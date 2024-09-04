import { POST } from "./fetch-auth-action.ts";

export const signInApi = async (params) => {
  const URL = "/api/auth/login";
  const response = POST(URL, params, null);
  return response;
};

export const signInApiCopy = async (param) => {
  const URL = "/api/auth/login";
  const response = POST(URL, param, null);
  return response;
};
