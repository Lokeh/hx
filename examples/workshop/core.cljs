(ns workshop.core
  (:require [devcards.core :as dc :include-macros true]
            ["react" :as react]
            [hx.react :as hx]))

(defn Example [props]
  (react/createElement "div" nil (prn-str props)))

(dc/defcard hiccup
  (hx/f [:div {:style {:background-color "red"}}
         [:h1 "foo"]
         [:button {:on-click #(js/alert "hi")} "click"]]))

(hx/defnc DefncExample [{:keys [foo children]}]
  [:<>
   [:div "mm"]
   [:div foo]
   (let [x 1
         y 2]
     [:div (+ x y)])
   (for [n [1 2 3]]
     [:div {:key n} (+ n 1)])
   children])

(dc/defcard defnc
  (hx/$ DefncExample {:foo "bar"} "child"))

(hx/defnc Rc [{:keys [children]}]
  [:div
   (children 3)])

(dc/defcard render-fn-child
  (hx/$ Rc
        (fn [n]
          [:<>
           [:div (hx/$ "span" "hi")]
           [:span {:style {:color "red"}} (+ n 1)]])))

(hx/defnc Shallow* [{:keys [name]}]
  [:div "Hello " [:span {:style {:color "blue"}} name] "!"])

(dc/defcard shallow
  (hx/shallow-render (Shallow* {:name "Will"} nil)))

(hx/defcomponent ClassComp
  (constructor [this]
               this)
  (render [this]
          [:h1 "foo"]))

(dc/defcard class-component
  (hx/$ ClassComp))

(def some-context (react/createContext))

(hx/defnc ContextConsumer [_]
  [:div
   [(.-Consumer some-context)
    (fn [v]
      [:div v])]])

(hx/defnc ContextProvider [_]
  [(.-Provider some-context)
   {:value "context value"}
   [:div
    [ContextConsumer]]])

(dc/defcard context
  (hx/$ ContextProvider))

(hx/defnc RefConsumer* [{:keys [on-click] :as props} ref]
  [:button {:ref ref :on-click on-click} "Click me"])

(def RefConsumer (react/forwardRef RefConsumer*))

(hx/defnc RefProvider [_]
  (def ref (react/createRef))
  [RefConsumer {:ref ref :on-click #(println ref)}])

(dc/defcard ref
  (hx/$ RefProvider))

(hx/defnc ComponentOne [_]
  [:<>
   [:div "hi"]
   [:div "bye"]])

(dc/defcard strict-mode
  (hx/f
   [react/StrictMode
    [:div "hello"]
    [ComponentOne]]))
