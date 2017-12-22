help: ## Prints help for targets with comments
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

detekt: ## Runs detekt checks
	./gradlew detektCheck --stacktrace

lint: ## Runs lint static analyze
	./gradlew lintDebug --stacktrace

unit: ## Runs unit tests
	./gradlew testDebugUnitTest --stacktrace

clean: ## Cleans the build
	./gradlew clean --stacktrace

ktlint: ## Runs ktlint checks
	./gradlew clean ktlintCheck --stacktrace

uptodate: ## Runs uptodate checks
	./gradlew uptodate

format: ## Runs ktlint format
	./gradlew ktlintFormat

check: detekt lint unit uptodate ## Runs detekt, ktlint, lint, unit & uptodate