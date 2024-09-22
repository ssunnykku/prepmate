import {createBrowserRouter} from "react-router-dom";
import {NotFoundPage} from "@/pages/NotFoundPage.tsx";
import {Error} from "@/pages/Error.tsx";
import {Home} from "@/pages/Home.tsx";
import {Interview} from "@/pages/Interview.tsx";

const Router = createBrowserRouter([
    {
        path: '/',
        element: <Home />,
        errorElement: <Error />,

    },
    {
        path: '/interviews',
        element: <Interview />,
        errorElement: <Error />,

    },
    {
        path: '*',
        element: <NotFoundPage />,
    },
]);

export default Router;