import {createBrowserRouter} from "react-router-dom";
import {NotFoundPage} from "@/pages/NotFoundPage.tsx";
import {Error} from "@/pages/Error.tsx";
import {Home} from "@/pages/Home.tsx";

const Router = createBrowserRouter([
    {
        path: '/',
        element: <Home />,
    },
    {
        path: '*',
        element: <NotFoundPage />,
        errorElement: <Error />,
    },
]);

export default Router;