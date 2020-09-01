(ns drfa-clojure.core
  (:gen-class)
  [:require [clojure.string :as str]
            [clojure.java.io :as io]])

(def delimiter #"\|")
(def pipe "|")

(defn readFile [fname]
  (with-open [rdr (clojure.java.io/reader fname)]
    (doall (line-seq rdr))))

(defn split-line [lines]
  (map (fn [y]
         (str/split y delimiter)) lines))

(defrecord column-matchers [columnName columnIndex expression])
(defrecord column-match-result [columnName base-value target-value match-status])

(defn extract [line indexes]
  (into [] (map (fn [x] (nth line x)) indexes)))

(defn selected-fields [line field-indexes]
  (extract line field-indexes))

(defn primary-key [line key-indexes]
  (apply str (interpose pipe (extract line key-indexes))))

(defn to-map [lines key-indexes field-indexes]
  (into {} (for [line lines] [(primary-key line key-indexes) (selected-fields line field-indexes)])))

(defn match-row [base-row target-row column-matchers]
  (for [matcher column-matchers]
    (let [column-name (:columnName matcher) column-index (:columnIndex matcher) expression (:expression matcher)]
      (let [base-value (base-row column-index) target-value (target-row column-index)]
        (if (expression base-value target-value)
          (->column-match-result column-name base-value target-value :matched)
          (->column-match-result column-name base-value target-value :not-matched))))))

(defn match [base-map target-map column-matchers]
  (remove nil? (let [keys (keys base-map)]
                 (for [key keys]
                   (if (contains? target-map key)
                     (let [base-row (base-map key) target-row (target-map key)]
                       (match-row base-row target-row column-matchers)))))))





