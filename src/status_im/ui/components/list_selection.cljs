(ns status-im.ui.components.list-selection
  (:require [re-frame.core :as re-frame]
            [status-im.utils.handlers :as handlers]
            [status-im.i18n :as i18n]
            [status-im.ui.components.action-sheet :as action-sheet]
            [status-im.ui.components.dialog :as dialog]
            [status-im.ui.components.react :as react]
            [status-im.utils.platform :as platform]
            [status-im.utils.http :as http]
            [status-im.ui.components.popup-menu.views :refer [show-desktop-menu]]))

(re-frame/reg-fx
 ::open-share
 (fn [content]
   (.share react/sharing (clj->js content))))

(handlers/register-handler-fx
 ::open-share
 (fn [_ [_ content]]
   {::open-share content}))

(defn open-share [content]
  (when (or (:message content)
            (:url content))
    (re-frame/dispatch [::open-share content])))

(defn show [options]
  (cond
    platform/ios?     (action-sheet/show options)
    platform/android? (dialog/show options)
    platform/desktop? (show-desktop-menu (->> (:options options) (remove nil?)))))

(defn- platform-web-browser []
  (if platform/ios? :t/browsing-open-in-ios-web-browser :t/browsing-open-in-android-web-browser))

(defn browse [link]
  (show {:title       (i18n/label :t/browsing-title)
         :options     [{:label  (i18n/label :t/browsing-open-in-status)
                        :action #(re-frame/dispatch [:browser.ui/open-url link])}
                       {:label  (i18n/label (platform-web-browser))
                        :action #(.openURL react/linking (http/normalize-url link))}]
         :cancel-text (i18n/label :t/browsing-cancel)}))

(defn browse-in-web-browser [link]
  (show {:title       (i18n/label :t/browsing-title)
         :options     [{:label  (i18n/label (platform-web-browser))
                        :action #(.openURL react/linking (http/normalize-url link))}]
         :cancel-text (i18n/label :t/browsing-cancel)}))

(defn browse-dapp [link]
  (show {:title       (i18n/label :t/browsing-title)
         :options     [{:label  (i18n/label :t/browsing-open-in-status)
                        :action #(re-frame/dispatch [:browser.ui/open-url link])}]
         :cancel-text (i18n/label :t/browsing-cancel)}))
