import './App.css'
import {RouterProvider} from "react-router-dom";
import router from './Router.tsx';
import GlobalStyle from "@/styles/GlobalStyle.tsx";

function App() {

  return (
      <>
        <RouterProvider router={router} />
        <GlobalStyle />
      </>
  )
}

export default App
