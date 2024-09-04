import Home from "../component/main/Home";
import SignIn from "../component/sign/SignIn";
import SignUp from "../component/sign/SignUp";

const routes = [
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/signIn",
    element: <SignIn />,
  },
  {
    path: "/signUp",
    element: <SignUp />,
  },
];

export default routes;
