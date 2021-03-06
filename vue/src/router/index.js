import Vue from "vue";
import Router from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/Login.vue";
import Logout from "../views/Logout.vue";
import Register from "../views/Register.vue";
import store from "../store/index";
import Add from "@/views/Add.vue";
import Landmark from "@/views/Landmark.vue";
import NotFound from "@/views/NotFound.vue";
import Itineraries from "@/views/Itineraries.vue";
import SendEmail from "@/views/SendEmail.vue";
import ItineraryRoute from "@/views/ItineraryRoute.vue";

Vue.use(Router);

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home",
      component: Home,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "/add",
      name: "add",
      component: Add,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "/landmarks/:id",
      name: "landmarks",
      component: Landmark,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "/itineraries",
      name: "itineraries",
      component: Itineraries,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "/itineraries/:id/email",
      name: "email",
      component: SendEmail,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "/routes/:id",
      name: "routes",
      component: ItineraryRoute,
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: "*",
      name: "not-found",
      component: NotFound,
      meta: {
        requiresAuth: false,
      },
    },
  ],
});

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some((x) => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === "") {
    next("/login");
  } else {
    // Else let them go to their next destination
    if (to.name === "landmarks") {
      const landmark = store.state.allLandmarks.find(
        (l) => l.landMarkId == to.params.id
      );
      if (!landmark) {
        next({ name: "not-found" });
      }
    }

    next();
  }
});

export default router;
