import { BrowserRouter, Route, Routes } from "react-router-dom";
import routes from "./routes/routes";
import { signInApi } from "./service/testService";

function App() {
  return (
    <BrowserRouter>
      <AppComponent />
    </BrowserRouter>
  );
}

const AppComponent = () => {
  const test = async () => {
    const param = {
      email: "xormsdlrnt@naver.com",
      password: "rmEk5712!!",
    };
    const response = await signInApi(param);
    if (response.status === 200) {
      console.log(response.data);
    }
  };

  test();

  return (
    <div className="App">
      <div className="header"> header</div>
      <Layout>
        <Routes>
          {routes.map((route) => (
            <Route key={route.path} path={route.path} element={route.element} />
          ))}
        </Routes>
      </Layout>
      <div className="header"> footer</div>
    </div>
  );
};

const Layout = ({ children }) => {
  return <div id="body">{children}</div>;
};

export default App;
