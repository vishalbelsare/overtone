(ns overtone.api
  (:import [java.lang.management ManagementFactory])
  (:use [overtone.libs boot-msg]
        [overtone.helpers.ns])
  (:require clojure.stacktrace
            [overtone.config store]
            [overtone version osc speech]
            [overtone.algo chance scaling trig fn lists]
            [overtone.sc bindings buffer bus envelope example info
             ugens defcgen node sample server synth clock
             foundation-groups dyn-vars trig vbap]
            [overtone.sc.cgens oscillators demand mix dyn io buf-io env tap
             line freq beq-suite berlach ;; bhob
             fx info]
            [overtone.music rhythm pitch tuning time]
            [overtone.studio mixer inst util fx wavetable midi midi-player core]
            [overtone.repl ugens examples shell inst debug graphviz]
            [overtone.libs asset event]
            [overtone.samples freesound]
            [overtone.helpers.doc :refer [fs]]))


;; Currently the default lein setting drastically reduces performance in
;; return for a 200ms improvement of the startup time. See:
;; https://github.com/technomancy/leiningen/pull/1230
(defonce __PRINT_TIERED_COMPILATION_WARNING__
  (let [compiler-bean (ManagementFactory/getCompilationMXBean)
        compiler-name (.getName compiler-bean)
        runtime-bean (ManagementFactory/getRuntimeMXBean)
        input-args    (.getInputArguments runtime-bean)]

    (when-let [_arg (and (re-find #"Tiered" compiler-name)
                         (some #(re-find #"TieredStopAtLevel=1" %)
                               input-args))]
      (println
       (fs "**********************************************************
            WARNING: JVM argument TieredStopAtLevel=1 is active, and may
            lead to reduced performance. This happens to currently be
            the default lein setting:

            https://github.com/technomancy/leiningen/pull/1230

            If you didn't intend this JVM arg to be specified, you can
            turn it off in your project.clj file or your global
            ~/.lein/profiles.clj file by adding the key-val

            :jvm-opts ^:replace []
              **********************************************************")))))

(def immigrated-namespaces
  ['overtone.algo.chance
   'overtone.algo.fn
   'overtone.algo.lists
   'overtone.algo.scaling
   'overtone.algo.trig
   'overtone.config.store
   'overtone.libs.asset
   'overtone.libs.event
   'overtone.music.pitch
   'overtone.music.rhythm
   'overtone.music.time
   'overtone.music.tuning
   'overtone.osc
   'overtone.repl.debug
   'overtone.repl.examples
   'overtone.repl.graphviz
   'overtone.repl.inst
   'overtone.repl.shell
   'overtone.repl.ugens
   'overtone.samples.freesound
   'overtone.sc.bindings
   'overtone.sc.buffer
   'overtone.sc.bus
   'overtone.sc.cgens.beq-suite
   'overtone.sc.cgens.berlach
   ;; 'overtone.sc.cgens.bhob
   'overtone.sc.cgens.buf-io
   'overtone.sc.cgens.demand
   'overtone.sc.cgens.dyn
   'overtone.sc.cgens.env
   'overtone.sc.cgens.freq
   'overtone.sc.cgens.fx
   'overtone.sc.cgens.info
   'overtone.sc.cgens.io
   'overtone.sc.cgens.line
   'overtone.sc.cgens.mix
   'overtone.sc.cgens.oscillators
   'overtone.sc.cgens.tap
   'overtone.sc.clock
   'overtone.sc.defcgen
   'overtone.sc.dyn-vars
   'overtone.sc.envelope
   'overtone.sc.example
   'overtone.sc.foundation-groups
   'overtone.sc.info
   'overtone.sc.node
   'overtone.sc.sample
   'overtone.sc.server
   'overtone.sc.synth
   'overtone.sc.trig
   'overtone.sc.ugens
   'overtone.sc.vbap
   'overtone.speech
   'overtone.studio.core
   'overtone.studio.fx
   'overtone.studio.inst
   'overtone.studio.midi
   'overtone.studio.midi-player
   'overtone.studio.mixer
   'overtone.studio.wavetable
   'overtone.version])

(defn immigrate-overtone-api []
  (apply immigrate immigrated-namespaces))
