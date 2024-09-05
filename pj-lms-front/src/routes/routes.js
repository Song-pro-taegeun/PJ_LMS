import Home from "../component/main/Home";
import SignIn from "../component/sign/SignIn";
import SignUp from "../component/sign/SignUp";
import SignInCopy from "../component/sign/SignInCopy";
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
  {
    path: "/signInCopy",
    element: <SignInCopy />,
  },
];

export default routes;
