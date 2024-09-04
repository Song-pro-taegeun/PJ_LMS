import { POST } from "./fetch-auth-action.ts";

export const signInApi = async (params) => {
  const URL = "/api/auth/login";
  const response = POST(URL, params, null);
  return response;
};
