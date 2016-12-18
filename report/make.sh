#!/usr/bin/env bash
cd report
latex report.tex

google-chrome ./report/report.pdf