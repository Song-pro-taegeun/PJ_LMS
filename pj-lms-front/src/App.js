import { BrowserRouter, Route, Routes } from "react-router-dom";
import routes from "./routes/routes";
import Header from "./component/main/Header";
import Footer from "./component/main/Footer";
import "./css/home.css";

function App() {
  return (
    <BrowserRouter>
      <AppComponent />
    </BrowserRouter>
  );
}

const AppComponent = () => {
  return (
    <div className="App">
      <Header />
      <Layout>
        <Routes>
          {routes.map((route) => (
            <Route key={route.path} path={route.path} element={route.element} />
          ))}
        </Routes>
      </Layout>
      <Footer />
    </div>
  );
};

const Layout = ({ children }) => {
  return <div id="body">{children}</div>;
};

export default App;
